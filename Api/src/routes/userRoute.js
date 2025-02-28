'use strict'
const { Router } = require('express');
const api = Router();
var userController = require('../controllers/userController');


api.post('/find_user/', userController.seleccionar_usuario);
api.post('/insert_user/', userController.insertar_usuario);
api.put('/update_user/:id', userController.actualizar_usuario);
api.patch('/change_password/', userController.cambiar_clave_usuario);
api.delete('/delete_user/:id', userController.eliminar_usuario);
api.post('/send_email/', userController.enviar_correo);
api.post('/send_commentary/', userController.enviar_comentario);

module.exports = api;