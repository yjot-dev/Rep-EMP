# EMPRENDIMIENTO PRIMARIA (EMP)
EMP es una aplicación móvil educativa y gamificada, diseñada para enseñar conceptos fundamentales de emprendimiento a través de una experiencia interactiva y progresiva. El objetivo principal es motivar al usuario a aprender mediante lecciones estructuradas y ejercicios prácticos, mientras sigue su progreso y gestiona su perfil de aprendizaje.

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
El flujo de uso de la aplicación está diseñado para ser intuitivo y completo, guiando al usuario a través de los siguientes pasos:

1. Acceso y Autenticación de Usuario: Al iniciar la aplicación, el usuario se encuentra con una pantalla de Login clara y funcional. Desde aquí, tiene tres opciones directas:
   - Iniciar Sesión: Los usuarios existentes pueden acceder rápidamente ingresando su nombre de usuario/correo y contraseña.
   - Registrarse: Los nuevos usuarios son dirigidos a una vista de Registro donde pueden crear su cuenta proporcionando un nombre de usuario, correo electrónico y contraseña.
   - Recuperar Clave: Si un usuario olvida su contraseña, puede ingresar su correo para recibir un código de verificación. Tras validar el código, puede establecer una nueva contraseña y recuperar el acceso a su cuenta.
2. Navegación en el Menú Principal: Una vez autenticado, el usuario accede al núcleo de la aplicación, que presenta una barra de navegación superior con cuatro secciones principales, cada una ofreciendo una funcionalidad clave:
   - Perfil de Usuario: Esta es la sección personal del usuario. Aquí puede visualizar y actualizar su información (foto de perfil, nombre de usuario, correo y contraseña). Para garantizar la seguridad, cualquier actualización o la eliminación de la cuenta requiere una verificación mediante un código enviado a su correo.
   - Módulos Educativos (Unidades): El corazón del aprendizaje. En esta vista, el usuario avanza a través de lecciones estructuradas sobre emprendimiento. El contenido se presenta mediante ejercicios gamificados como verdadero/falso, completar oraciones y emparejar conceptos, obteniendo un puntaje al finalizar cada nivel.
   - Proyectos Prácticos: Para inspirar la acción, esta sección ofrece una lista de ideas de proyectos de emprendimiento. Cada proyecto incluye información detallada que sirve como guía y ejemplo para que el usuario pueda empezar a conceptualizar su propio negocio.
   - Progreso y Feedback (Puntaje): Esta vista funciona como un panel de control del rendimiento del usuario. Muestra métricas clave como el puntaje total acumulado, el tiempo total de estudio en la app y el porcentaje de avance del curso. Además, incluye una función para que el usuario pueda enviar sus opiniones o sugerencias directamente al desarrollador.

En resumen, Emprendimiento Primaria empodera a los usuarios en su camino de aprendizaje sobre negocios, combinando una sólida arquitectura técnica con una experiencia de usuario gamificada y fácil de seguir para reportar, aprender y monitorear su progreso de manera efectiva.
  
# Ver video Demo
[Ver en YouTube](https://youtu.be/8ceBytM3tVI)

# Contribución
- Haz un fork del repositorio
- Crea una rama con tu feature: git checkout -b feature/nueva-funcionalidad
- Haz commit de tus cambios: git commit -m "Agrega nueva funcionalidad"
- Haz push a la rama: git push origin feature/nueva-funcionalidad
- Abre un Pull Request

# Licencia
Este proyecto está bajo la licencia GPL-3.0. Consulta el archivo LICENSE para más detalles.
