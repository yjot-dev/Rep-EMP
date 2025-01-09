'use strict'
// Instanciar rutas
var usuarioRoute = require('./src/routes/userRoute');

// Instanciar modulos
const express = require('express')
const https = require('https');
const fs = require('fs');
const app = express()

// Configurar puerto
app.set('port', process.env.PORT || 443)

// Usar JSON con un limite de datos para el body request
app.use(express.json({ limit: '20mb' }))
// Usar rutas
app.use('/api', usuarioRoute)

// Lee el certificado y la clave privada 
const privateKey = fs.readFileSync('./src/certificate/mykey.key'); 
const certificate = fs.readFileSync('./src/certificate/mycert.crt'); 
const credentials = { key: privateKey, cert: certificate };

// Crea el servidor HTTPS 
const httpsServer = https.createServer(credentials, app);

// Escuchar servicio
httpsServer.listen(app.get('port'), (error) => {
    if (error) {
        console.log(`Sucedió un error: ${error}`);
    }else{
        console.log(`Servidor corriendo en el puerto: ${app.get('port')}`);
    }
});