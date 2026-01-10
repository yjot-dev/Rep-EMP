# GAMIFICACION SOBRE EMPRENDIMIENTO
Esta app está orientada a la educación, cuenta con varios ejercicios sobre la 
temática de emprendimiento, tiene cuatro modelos de actividades.

# Características principales
- 🪟 Interfaz moderna con Jetpack Compose
- 🌐 Navegación con Navigation Component
- 📊 Integración con ViewModel + StateFlow
- 🎨 Patrón de diseño arquitectónico con MVVM + Hexagonal
- 🧩 Inyección de dependencias con Hilt
- 💽 Base de datos remota con MySQL y la API RESTful con Node
- 📱 Compatible con Android 7.0 (API 24) en adelante

# Instalación
- Clona el repositorio: git clone https://github.com/yjot-dev/Rep-EMP.git
- Abre el proyecto en Android Studio (Giraffe o superior)
- Sincroniza dependencias con Gradle
- Conecta un dispositivo o emulador y ejecuta la app

# Tecnologías usadas
- Kotlin
- Jetpack Compose
- AndroidX (Navigation, Lifecycle, Core KTX)
- Material 3

# Uso
- Al abrir la app, se muestra la vista de inicio *Login* donde se ubican tres botones con las opciones de inicia sesion, registrarse y recuperar clave, en el caso de ya
  tener un usuario se puede directamente iniciar sesion en la misma vista rellenando los campos de usuario/correo y contraseña.
- Si no se tiene un usuario se lo debe crear dando click en el boton Registrarse, luego se abre una vista *Registrarse* hay se debe rellenar los campos usuario, correo y contraseña para
  luego hacer click en el boton Crear Usuario.
- Si no se acuerda de la clave debe hacer click en el boton Recuperar Clave, luego se abre una vista "Recuperar Clave" hay debe escribir su correo y luego dar click en el boton
  Enviar Codigo, le llegara un codigo a su correo y ese codigo debe escribirlo en la vista emergente que le aparece, luego debe dar enter y escribir la nueva clave en el campo de la vista
  "Recuperar Clave" para finalmente dar click en el boton Cambiar Clave.
- Si ya inicio sesion le aparecera la vista "Menu" que contiene cuatro opciones en la barra superior y en la parte de la vista se aparecera cada vista de la opcion seleccionada.
- La opcion 1 es la vista *Usuario*, hay podra ver su foto de perfil, su usuario, correo y clave (encriptada pero con opcion de ver), tambien puede actualizar dicha informacion o borrar
  su cuenta, para ello debe enviar un codigo a su correo y con dicho codigo verificar la accion correspondiente.
- La opcion 2 es la vista *Unidades* hay podra realizar cada leccion del contenido sobre la tematica de emprendimeinto ofrecido, cuenta con ejercicios de verdadero o falso, completar la
  oracion y ordenar pares de tarjetas (ejemplo con titulo del concepto), al finalizar cada nivel se muestra el puntaje obtenido.
- La opcion 3 es la vista *Proyectos* hay podra ver una lista de proyectos de emprendimiento con informacion de cada uno de ellos, que ayudara a darle ideas de como empezar su negocio.
- La opcion 4 es la vista *Puntaje* hay podra ver el puntaje acumulado que involucra su puntaje total, el tiempo total de estudio en la app y el porcentaje del avance de todo el curso,
  ademas puede enviar una opinion a manera de retroalimentacion al correo configurado en la API RESTful.
  
# Ver video Demo
[Ver en YouTube](https://youtu.be/cwfOSbDeAXY?si=Im9SBVUkK9a6bT8T)

# Contribución
- Haz un fork del repositorio
- Crea una rama con tu feature: git checkout -b feature/nueva-funcionalidad
- Haz commit de tus cambios: git commit -m "Agrega nueva funcionalidad"
- Haz push a la rama: git push origin feature/nueva-funcionalidad
- Abre un Pull Request

# Licencia
Este proyecto está bajo la licencia GPL-3.0. Consulta el archivo LICENSE para más detalles.
