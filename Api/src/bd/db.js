'use strict'
const mysql = require('mysql2'), 
      data = require('./credenciales.json'),
      dataConexion = {
        host: data.mysql.host,
        port: data.mysql.port,
        user: data.mysql.user,
        password: data.mysql.password,
        database: data.mysql.database
      }

const pool = mysql.createPool(dataConexion)

pool.getConnection((err, connection) => {
    if (err) {
      if (err.code === 'PROTOCOL_CONNECTION_LOST') {
        console.error('La conexion a la base de datos fue cerrada.')
      }
      if (err.code === 'ER_CON_COUNT_ERROR') {
        console.error('La base de datos ha tenido demasiadas conexiones.')
      }
      if (err.code === 'ECONNREFUSED') {
        console.error('La conexion a la base de datos fue rechazada.')
      }
    }

    
if (connection) connection.release()

return
})

module.exports = pool;