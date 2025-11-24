# Tejedora y Punto - API REST

## 🚀 Cómo iniciar el sistema

### Requisitos previos
- **XAMPP** instalado
- **Java 21** o superior
- **IDE** como VS Code

### Paso a paso para iniciar el sistema

#### 1. Iniciar servicios de base de datos (XAMPP)

**Abrir XAMPP Control Panel**
   - Busca "XAMPP Control Panel" en el menú de inicio
   - Ejecutar como administrador (recomendado)

#### 2. Verificar la base de datos (Opcional)

**Verificar la base de datos**
   - La base de datos `tejedoraypunto` se creará automáticamente
   - Las tablas se crearán al iniciar Spring Boot

#### 3. Iniciar la aplicación Spring Boot

##### Desde VS Code
1. **Abrir el proyecto** en VS Code
2. **Ejecutar la aplicación**:
   - Abrir `tejedoraypuntoApplication.java`
   - Hacer clic derecho → "Run Java"
   - O usar `Ctrl + F5`

### 🌐 Acceder a la documentación de Swagger

Una vez que tengas el servidor ejecutándose, puedes acceder a la documentación:

Una vez que tengas el servidor ejecutándose, puedes acceder a la documentación de Swagger en las siguientes URLs:

#### Swagger UI (Interfaz gráfica)
```
http://localhost:8082/swagger-ui.html
```

#### API Docs (JSON)
```
http://localhost:8082/api-docs
```

### 🎯 Resumen rápido de inicio

1. ✅ **Iniciar XAMPP** (Apache + MySQL)
2. ✅ **Ejecutar Spring Boot** (puerto 8082)
3. ✅ **Abrir Swagger**: `http://localhost:8082/swagger-ui.html`
4. ✅ **¡Listo para usar la API!**

---

## 🔑 Credenciales de prueba

Para probar las funcionalidades de la aplicación, puedes utilizar las siguientes credenciales:

### Usuario Administrador
```
Email: tejedoraypunto@gmail.com
Contraseña: admin123
```

### Usuario Cliente
```
Email: cliente@gmail.com
Contraseña: cliente123
```
