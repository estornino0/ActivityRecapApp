# Strava Activity Recap

[Strava](https://www.strava.com/features) es una plataforma para almacenar, analizar y compartir actividades deportivas. Este proyecto tiene como objetivo consumir la API REST de Strava para mostrar al usuario un breve recap de sus actividades en cierto periodo de tiempo.



## Tecnologías Utilizadas

### Servidor
- **Java**: Lenguaje de programación principal.
- **Spring Framework**:
    - **Spring Boot**: Para manejar las solicitudes al servidor siguiendo una arquitectura MVC.
    - **FeignClient**: Para interactuar con la API de Strava.
    - **JUnit**: Para realizar tests unitarios y asegurar la calidad del código.


### Funcionalidades
El usuario da autorización para que la aplicación pueda leer las actividades que tiene almacenadas en Strava.

La autorización se realiza mediante el flujo de credenciales de cliente del estándar **OAuth2.0**.
![](/images/Pasted image 20240725200315.png)

Una vez otorgada la autorización el usuario puede seleccionar un intervalo de fechas para ver el resumen de sus actividades en ese periodo específico.
El resumen muestra la cantidad de actividades completadas en ese periodo, la distancia y duración acumulada, y un mapa de calor que permite ver las zonas transitadas con mayor frecuencia.

![](/images/Pasted image 20240725155020.png)