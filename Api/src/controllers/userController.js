'use strict'
const util = require('util');
const connection = require('../bd/db');
const query = util.promisify(connection.query).bind(connection);
const bcrypt = require('bcrypt');
const nodemailer = require('nodemailer');

// Verifica si el objeto esta vacio
function isEmptyObject(obj) {
    return !Object.keys(obj).length;
};

// Seleccionar usuario
const seleccionar_usuario = async function(req, res) {
    try {
        const { nombre, correo, clave } = req.body;

        // Consulta para obtener la clave encriptada del usuario
        const sql1 = `SELECT clave FROM usuarios WHERE correo = ? OR nombre = ?`;
        const reg1 = await query(sql1, [correo, nombre]);
        
        if (isEmptyObject(reg1)) {
            return res.status(500).send("Error clave no encontrada");
        }

        const data = Object.values(JSON.parse(JSON.stringify(reg1)));
        const claveHash = data[0].clave;

        // Comparar la clave ingresada con la clave encriptada almacenada
        const esLaClave = bcrypt.compareSync(clave, claveHash);
        if (!esLaClave) {
            return res.status(401).send("Error clave incorrecta");
        }

        // Consulta para obtener los datos del usuario
        const sql2 = `SELECT * FROM usuarios WHERE (correo = ? OR nombre = ?) AND clave = ?`;
        const reg2 = await query(sql2, [correo, nombre, claveHash]);

        if (isEmptyObject(reg2)) {
            return res.status(500).send("Error usuario no encontrado");
        }

        const user = reg2[0];

        // Convertir el campo foto (MediumBlob) a una cadena Base64
        if (user.foto) { 
            user.foto = Buffer.from(user.foto).toString('base64');
        }

        res.status(200).send(user);
    } catch (error) {
        console.error("Error al seleccionar usuario: ", error);
        res.status(500).send("Error del servidor");
    }
};

// Actualizar usuario
const actualizar_usuario = async function(req, res) {
    try {
        const id = req.params.id;
        // Desestructura clave y foto, y recoge el resto de los datos
        let { clave, foto, ...resto } = req.body; 

        // Consulta para obtener la clave del usuario
        const sql1 = `SELECT clave FROM usuarios WHERE id = ?`;
        const reg1 = await query(sql1, [id]);

        if (isEmptyObject(reg1)) {
            return res.status(500).send("Error clave no encontrada");
        }

        const data = Object.values(JSON.parse(JSON.stringify(reg1)));
        const claveHash = data[0].clave;

        // Comparar la clave ingresada con la clave hash almacenada
        const esLaClave = bcrypt.compareSync(clave, claveHash);
        if (!esLaClave) {
            clave = bcrypt.hashSync(clave, 10);
        }else{
            clave = claveHash
        }

        // Convertir una cadena Base64 a MediumBlob
        if (foto) { 
            foto = Buffer.from(foto, 'base64');
        }

        // Construir el objeto para actualizar
        const usuarioEditado = {
            ...resto,
            clave: clave,
            foto: foto
        };

        // Construir la consulta de actualización
        const sql2 = `UPDATE usuarios SET ? WHERE id = ?`;
        const reg2 = await query(sql2, [usuarioEditado, id]);

        res.status(200).send(reg2);
    } catch (error) {
        console.error("Error al actualizar usuario: ", error);
        res.status(500).send("Error del servidor");
    }
};

// Cambiar clave de usuario
const cambiar_clave_usuario = async function(req, res) {
    try {
        const { correo, clave } = req.body

        // Encriptar la clave
        const claveHash = bcrypt.hashSync(clave, 10);

        // Construir la consulta de cambio de clave
        const sql = `UPDATE usuarios SET clave = ? WHERE correo = ?`;
        const reg = await query(sql, [claveHash, correo]);

        res.status(200).send(reg);
    } catch(error) {
        console.error("Error al cambiar clave de usuario: ", error);
        res.status(500).send("Error del servidor");
    }
}

// Insertar usuario
const insertar_usuario = async function(req, res) {
    try {
        // Desestructura clave y recoge el resto de los datos
        const { correo, clave, ...resto } = req.body;

        // Encriptar la clave
        const claveHash = bcrypt.hashSync(clave, 10);

        // Construir el objeto para insertar
        const usuarioNuevo = {
            ...resto,
            correo: correo,
            clave: claveHash
        };

        // Consulta el correo electronico para verificar si existe
        const sql1 = `SELECT * FROM usuarios WHERE correo = ?`;
        const reg1 = await query(sql1, correo);
        if(!isEmptyObject(reg1)){
            return res.status(500).send("Error correo existente");
        }

        // Construir la consulta de inserción
        const sql2 = `INSERT INTO usuarios SET ?`;
        const reg2 = await query(sql2, usuarioNuevo);

        res.status(200).send(reg2);
    } catch (error) {
        console.error("Error al insertar usuario: ", error);
        res.status(500).send("Error del servidor");
    }
};

// Eliminar usuario
const eliminar_usuario = async function(req, res) {
    try{
        const id = req.params.id

        // Construir la consulta de eliminación
        const sql = `DELETE FROM usuarios WHERE id = ?`;
        const reg = await query(sql, [id]);

        res.status(200).send(reg);
    } catch (error) {
        console.error("Error al eliminar usuario: ", error);
        res.status(500).send("Error del servidor");
    }
}

// Enviar email a usuario
const enviar_correo = async function(req, res) {
    try { 
        const from = 'emprendimiento2020g7h2@gmail.com'
        
        // Configura servicio del correo electronico
        const transporter = nodemailer.createTransport({ 
            service: 'gmail', // Servicio usado 
            auth: { 
                user: from, 
                pass: 'pozy achq jhyi fmcl' 
            },
            tls: { rejectUnauthorized: false } // Desactiva la verificación SSL
        });

        const { to, subject, text } = req.body;
        // Construir cuerpo del correo electronico
        const mailOptions = { 
            from: from, 
            to: to, 
            subject: subject, 
            text: text 
        };

        // Consulta el correo electronico para verificar si existe
        const sql = `SELECT * FROM usuarios WHERE correo = ?`;
        const reg = await query(sql, to);
        if(isEmptyObject(reg)){
            return res.status(500).send("Error correo no encontrado");
        }

        // Envia correo electronico
        const info = await transporter.sendMail(mailOptions);

        res.status(200).send('Correo enviado con éxito a ' + info.accepted);
    } catch (error) { 
        console.error("Error al enviar correo electronico: ", error);
        res.status(500).send('Error del servidor');
    }
};

// Enviar comentario a empresa
const enviar_comentario = async function(req, res){
    try { 
        const from = 'emprendimiento2020g7h2@gmail.com'
        
        // Configura servicio del correo electronico
        const transporter = nodemailer.createTransport({ 
            service: 'gmail', // Servicio usado 
            auth: { 
                user: from, 
                pass: 'pozy achq jhyi fmcl' 
            },
            tls: { rejectUnauthorized: false } // Desactiva la verificación SSL
        });

        const { subject, text } = req.body;
        // Construir cuerpo del correo electronico
        const mailOptions = { 
            from: from, 
            to: from, 
            subject: subject, 
            text: text 
        };

        // Envia correo electronico
        const info = await transporter.sendMail(mailOptions);

        res.status(200).send('Correo enviado con éxito a ' + info.accepted);
    } catch (error) { 
        console.error("Error al enviar correo electronico: ", error);
        res.status(500).send('Error del servidor');
    }
};

module.exports = {
    seleccionar_usuario,
    actualizar_usuario,
    cambiar_clave_usuario,
    insertar_usuario,
    eliminar_usuario,
    enviar_correo,
    enviar_comentario
};