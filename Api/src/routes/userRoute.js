import { Router } from 'express';
const api = Router();
import { seleccionar_usuario, insertar_usuario, actualizar_usuario, cambiar_clave_usuario, eliminar_usuario, enviar_correo, enviar_comentario } from '../controllers/userController.js';

api.post('/find_user/', seleccionar_usuario);
api.post('/insert_user/', insertar_usuario);
api.put('/update_user/:id', actualizar_usuario);
api.patch('/change_password/', cambiar_clave_usuario);
api.delete('/delete_user/:id', eliminar_usuario);
api.post('/send_email/', enviar_correo);
api.post('/send_commentary/', enviar_comentario);

export { api };