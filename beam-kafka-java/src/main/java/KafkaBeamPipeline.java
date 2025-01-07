import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;

public class KafkaBeamPipeline {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline p = Pipeline.create(options);

        PCollection<String> lines = p.apply("ReadFromKafka", KafkaIO.<String, String>read()
                        .withBootstrapServers("localhost:9092")
                        .withTopic("training-exercises")
                        .withKeyDeserializer(StringDeserializer.class)
                        .withValueDeserializer(StringDeserializer.class)
                        .withoutMetadata())


                .apply("ExtractValues", MapElements
                        .via(new SimpleFunction<KV<String, String>, String>() {
                            @Override
                            public String apply(KV<String, String> input) {
                                return input.getValue();
                            }
                        }));

        PCollection<String> windowedLines = lines.apply("ApplyWindowing",
                Window.into(FixedWindows.of(Duration.standardMinutes(1))));

        windowedLines.apply("WriteToFile", TextIO.write()
                        .to("output/kafka-output")
                        .withSuffix(".txt")
                        .withWindowedWrites()
                        .withNumShards(1));

        // Ejecuta el pipeline
        p.run().waitUntilFinish();
    }
}