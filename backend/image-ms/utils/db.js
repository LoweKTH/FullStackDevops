const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('fullstack24', 'user', 'password', {
    host: 'fullstack24db',
    port: 3306,
    dialect: 'mysql',
    logging: false,
});

module.exports = { sequelize };
