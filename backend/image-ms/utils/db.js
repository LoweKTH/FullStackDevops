const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('fullstack24', 'user', 'password', {
    host: 'db',
    port: 3306,
    dialect: 'mysql',
    logging: false,
});

module.exports = { sequelize };
