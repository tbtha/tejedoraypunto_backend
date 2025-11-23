# Guía de Uso de JWT - Tejedora y Punto API

## 🔐 Configuración de Seguridad

### Endpoints Públicos (sin token)
- ✅ **POST** `/auth/login` - Login
- ✅ **GET** `/api/productos` - Listar productos
- ✅ **GET** `/api/productos/{id}` - Ver producto
- ✅ **GET** `/api/categorias` - Listar categorías
- ✅ **GET** `/api/categorias/{id}` - Ver categoría
- ✅ **GET/POST** `/api/imagenes/**` - Gestión de imágenes

### Endpoints Protegidos (requieren token)
- 🔒 **POST** `/api/productos` - Crear producto
- 🔒 **PUT** `/api/productos/{id}` - Actualizar producto
- 🔒 **DELETE** `/api/productos/{id}` - Eliminar producto
- 🔒 **POST** `/api/categorias` - Crear categoría
- 🔒 **PUT** `/api/categorias/{id}` - Actualizar categoría
- 🔒 **DELETE** `/api/categorias/{id}` - Eliminar categoría
- 🔒 **Todos** `/api/usuarios/**` - Gestión de usuarios

---

## 📝 Cómo Usar el Token

### Paso 1: Obtener el Token
**Endpoint:** `POST http://localhost:8082/auth/login`

**Body (JSON):**
```json
{
  "username": "tejedoraypunto@gmail.com",
  "password": "admin123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWplZG9yYXlwdW50b0BnbWFpbC5jb20iLCJpYXQiOjE3MDA3ODk..."
}
```

---

### Paso 2: Usar el Token en Thunder Client

#### Para endpoints protegidos:

1. **Headers Tab:**
   ```
   Authorization: Bearer {tu-token-aquí}
   ```

2. **Body Tab** (para POST/PUT):
   ```json
   {
     "nombre": "Nuevo Producto",
     "descripcion": "Descripción del producto",
     "precio": 25000,
     "stock": 5,
     "imagen": "img/otros/producto.jpg",
     "categoria_id": 1
   }
   ```

---

### Paso 3: Ejemplos Prácticos

#### ✅ Ejemplo 1: Listar Productos (SIN TOKEN)
```
GET http://localhost:8082/api/productos
```
No requiere headers especiales.

---

#### 🔒 Ejemplo 2: Crear Producto (CON TOKEN)

**Thunder Client:**
- **Method:** POST
- **URL:** `http://localhost:8082/api/productos`
- **Headers:**
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWplZG9yYXlwdW50...
  Content-Type: application/json
  ```
- **Body:**
  ```json
  {
    "nombre": "Chaleco Artesanal",
    "descripcion": "Tejido a mano con lana natural",
    "precio": 45000,
    "stock": 3,
    "imagen": "img/winter/chaleco_nuevo.jpeg",
    "activo": true,
    "categoria": {
      "id": 1
    }
  }
  ```

---

#### 🔒 Ejemplo 3: Actualizar Producto (CON TOKEN)

**Thunder Client:**
- **Method:** PUT
- **URL:** `http://localhost:8082/api/productos/1`
- **Headers:**
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWplZG9yYXlwdW50...
  Content-Type: application/json
  ```
- **Body:**
  ```json
  {
    "nombre": "Chaleco Bosque Actualizado",
    "descripcion": "Nueva descripción",
    "precio": 60000,
    "stock": 5,
    "imagen": "img/winter/chaleco1.jpeg",
    "activo": true,
    "categoria": {
      "id": 1
    }
  }
  ```

---

#### 🔒 Ejemplo 4: Eliminar Producto (CON TOKEN)

**Thunder Client:**
- **Method:** DELETE
- **URL:** `http://localhost:8082/api/productos/5`
- **Headers:**
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJsdWIiOiJ0ZWplZG9yYXlwdW50...
  ```

---

## 🧪 Probar sin Token (Debe Fallar)

Si intentas crear un producto SIN el token:

**Request:**
```
POST http://localhost:8082/api/productos
Body: { ... }
```

**Respuesta Esperada:**
```
Status: 403 Forbidden
```

---

## 👤 Usuarios Disponibles

### Usuario Administrador
```json
{
  "username": "tejedoraypunto@gmail.com",
  "password": "admin123"
}
```

### Usuario Cliente
```json
{
  "username": "cliente@gmail.com",
  "password": "cliente123"
}
```

---

## ⏱️ Expiración del Token

- **Duración:** 24 horas (86400000 ms)
- Cuando expira, debes volver a hacer login para obtener un nuevo token

---

## 🚨 Errores Comunes

### Error 403 Forbidden
- ❌ No incluiste el header `Authorization`
- ❌ El token está mal copiado (verifica que comience con "Bearer ")
- ❌ El token expiró (han pasado más de 24 horas)

### Error 401 Unauthorized
- ❌ Token inválido o corrupto
- ❌ Usuario no existe

---

## 📱 Integración en Frontend (Ejemplo con Fetch)

```javascript
// 1. Login y guardar token
async function login() {
  const response = await fetch('http://localhost:8082/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: 'tejedoraypunto@gmail.com',
      password: 'admin123'
    })
  });
  
  const data = await response.json();
  localStorage.setItem('token', data.token);
}

// 2. Usar token en peticiones protegidas
async function crearProducto(producto) {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8082/api/productos', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(producto)
  });
  
  return await response.json();
}

// 3. Petición pública (sin token)
async function listarProductos() {
  const response = await fetch('http://localhost:8082/api/productos');
  return await response.json();
}
```

---

## ✅ Checklist de Prueba

- [ ] Login exitoso y obtención de token
- [ ] GET productos sin token (debe funcionar)
- [ ] GET producto por ID sin token (debe funcionar)
- [ ] POST producto sin token (debe fallar con 403)
- [ ] POST producto con token (debe funcionar)
- [ ] PUT producto con token (debe funcionar)
- [ ] DELETE producto con token (debe funcionar)
- [ ] Mismo flujo para categorías
- [ ] Acceso a usuarios con token

---

¡Listo! Ahora tu API está protegida con JWT 🎉
