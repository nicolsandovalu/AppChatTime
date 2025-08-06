# 💬 Chat en Tiempo Real  
*Aplicación Android con Clean Architecture, WebSockets y Room*  

## 🚀 Características Principales  
- **Autenticación** simulada (email: `admin@chat.com` / pass: `1234`)  
- 📨 **Chat en tiempo real** usando WebSockets  
- 🖼️ **Envía imágenes** desde la galería  
- 💾 **Persistencia local** con Room Database  
- 🔄 Arquitectura **Clean Architecture** con Hilt (DI)  

## 🛠️ Stack Tecnológico  
| Capa          | Tecnologías                          |
|---------------|--------------------------------------|
| **UI**        | Jetpack Compose, ViewModel, LiveData |
| **Dominio**   | Casos de uso, modelos                |
| **Datos**     | Room, WebSockets (OkHttp)            |
| **DI**        | Hilt                                 |

## ⚙️ Configuración Requerida

### 1. Servidor WebSocket (Python)
```bash
python -m pip install websockets

# server.py
import asyncio
import websockets

async def handler(websocket):
    async for message in websocket:
        await websocket.send(message)

async def main():
    async with websockets.serve(handler, "0.0.0.0", 8080):
        await asyncio.Future()

asyncio.run(main())

// Constants.kt
const val WEBSOCKET_URL = "ws://10.0.2.2:8080"  // Para emulador
// const val WEBSOCKET_URL = "ws://[TU_IP]:8080" // Para dispositivo físico

##📌 Limitaciones Actuales
- 🔒 Autenticación hardcodeada
- 🌐 Imágenes solo visibles localmente
- 🔔 Sin notificaciones push

##🔮 Roadmap
- Perfiles de usuario
- Creación de salas dinámicas
- Sincronización offline
- Envío de archivos

