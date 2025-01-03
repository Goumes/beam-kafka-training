## **Enunciado del ejercicio: Ingeniero de Datos Local con Simulación de GCP**

### **Objetivo General:**

Diseñar un ejercicio práctico para aprender y simular tareas propias del rol de Ingeniero de Datos, utilizando Apache Beam, Kafka, y herramientas locales que emulen servicios de GCP como Pub/Sub, Dataflow y BigQuery, con soporte para contenerización en Docker.

---

### **Primera Parte: Creación de una Pipeline en Java con Apache Beam**

1. **Objetivo Principal:**

   - Diseñar una pipeline de Apache Beam utilizando Java y Maven.
   - Leer datos desde un flujo simulado de Kafka que actúa como Pub/Sub, procesar la información y almacenarla en formatos y estructuras equivalentes a servicios de GCP.

2. **Requisitos:**

   - **Entrada:**

     - Simula un flujo de mensajes de Kafka que incluya los siguientes campos:
       - `ejercicio`: Nombre del ejercicio (e.g., "Sentadilla").
       - `repeticiones`: Número de repeticiones (entero).
       - `sets`: Número de sets (entero).
       - `comentario`: Observaciones (opcional, cadena de texto).
       - `timestamp`: Fecha y hora del registro.
     - Configura un productor de Kafka que envíe mensajes de forma continua o intermitente.

   - **Procesamiento:**

     - Agrupa los datos por día (basado en el campo `timestamp`).
     - Calcula estadísticas adicionales, como:
       - Total de ejercicios registrados por día.
       - Promedio de repeticiones por ejercicio.
     - Realiza validaciones, como descartar mensajes incompletos o duplicados.

   - **Salida:**

     - Genera un archivo Excel utilizando [Apache POI](https://poi.apache.org/) que:
       - Contenga una hoja por día.
       - Incluya las estadísticas calculadas en una fila resumen.
     - Guarda los datos procesados en una base de datos local que emule BigQuery (SQLite o PostgreSQL).

3. **Extra:**

   - Implementa un registro de logs que indique cuándo se procesó cada mensaje y cualquier error encontrado.

---

### **Segunda Parte: Replicación en Python con Apache Beam**

1. **Objetivo Principal:**

   - Replicar la funcionalidad del pipeline en Python utilizando Apache Beam.
   - Ampliar la complejidad del procesamiento y salida.

2. **Requisitos:**

   - Implementa la misma funcionalidad descrita en la primera parte (Java) pero utilizando Python:

     - **Entrada:**
       - Lectura de datos desde un flujo de Kafka con los mismos campos especificados anteriormente.
     - **Procesamiento:**
       - Agrupación por día, cálculo de estadísticas y validaciones.
     - **Salida:**
       - Genera un archivo Excel y almacena los datos en una base de datos local equivalente a BigQuery.

   - Amplía la lógica para incluir:

     - Análisis de frecuencia: ¿Cuántas veces se repite un mismo ejercicio durante una semana?
     - Filtrado de datos: Descarta ejercicios con menos de 3 sets o 5 repeticiones.
     - Uso de funciones personalizadas (`DoFn`) para manejar transformaciones complejas.

   - **Salida:**

     - Genera el archivo Excel utilizando [Google Sheets API](https://developers.google.com/sheets) o [pandas con openpyxl](https://pandas.pydata.org/docs/reference/api/pandas.ExcelWriter.html).
     - Además de almacenar los datos en SQLite/PostgreSQL, guarda un resumen semanal en formato JSON en un directorio local.

   - **Expansión:**

     1. **Usar una API REST como intermediario:**
        - Configura un servidor REST: Crea un microservicio en Python (Flask/FastAPI), o Java (Spring Boot), que acepte mensajes desde el móvil a través de una llamada HTTP POST.
        - Envía los mensajes a Kafka: Este microservicio actuará como productor de Kafka, reenviando los datos al clúster de Kafka local.
        - En el móvil: Usa una aplicación como Postman para enviar datos al servidor REST.

3. **Extra:**

   - Implementa manejo de errores que registre los mensajes fallidos junto con la razón del fallo.
   - Introduce configuración por parámetros para que el pipeline pueda alternar entre Kafka y RabbitMQ.

---

### **Tercera Parte: Contenerización y Orquestación con Docker**

1. **Objetivo Principal:**

   - Contenerizar las pipelines desarrolladas (en Java y Python) y configurarlas para ejecutarse en un entorno aislado.

2. **Requisitos:**

   - Diseña un `Dockerfile` para cada versión del pipeline.
   - Usa `docker-compose` para orquestar los servicios necesarios:
     - Kafka o RabbitMQ.
     - Base de datos local (SQLite o PostgreSQL).
     - Volúmenes para almacenar los datos generados (Excel y JSON).

3. **Extra:**

   - Integra supervisión básica con Prometheus y Grafana para:
     - Monitorear la cantidad de mensajes procesados por minuto.
     - Identificar cuellos de botella en el pipeline.
   - Configura reinicios automáticos de contenedores en caso de fallos.

---

### **Cuarta Parte: Expansión y Escalabilidad (Opcional)**

1. **Integración de Batch y Streaming:**

   - Configura un modo de operación dual:
     - Modo streaming para procesar mensajes en tiempo real.
     - Modo batch para procesar datos históricos almacenados en archivos.

2. **Pruebas Automatizadas:**

   - Implementa pruebas unitarias e integradas que validen:
     - La transformación correcta de los datos.
     - La generación adecuada de los archivos de salida.
   - Usa frameworks como JUnit (Java) o pytest (Python).

3. **Simulación de Carga:**

   - Usa Apache JMeter o Locust para enviar mensajes masivos al pipeline y evaluar su rendimiento.

---

### **Resultados Esperados**

Al finalizar este ejercicio, deberías tener:

1. Una comprensión profunda de Apache Beam y su uso en entornos locales.
2. Experiencia en la replicación de servicios de GCP (Pub/Sub, Dataflow, BigQuery) utilizando herramientas locales.
3. Habilidades en contenerización y orquestación de sistemas distribuidos.
4. Un pipeline funcional y escalable que pueda adaptarse a un despliegue real en GCP.

¿Listo para comenzar? Si necesitas ejemplos específicos o aclaraciones técnicas, no dudes en pedir ayuda.

