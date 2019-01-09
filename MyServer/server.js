
const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var connectToDatabase = () => {
    return mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "",
        database: "blogs"
    });
}

app.get('/user', (req, res) => {
    let id = req.query.user_id;
    
    var con  = connectToDatabase();

    let sql = `select name,bdate,gender,email,addr,phone,pws from users where id=${id}`;
    con.query(sql, (err, result) => {
        if (err) {
            res.send({
                'result_code': 201,
                'message': `Can't get user info`
            });
        } else {
            if (result.length > 0) {
                let obj = result[0];
                res.send({
                    'result_code': 200,
                    'info': obj
                });
            } else {
                res.send({
                    'result_code': 203,
                    'message': 'user not found'
                });
            }
        }
    })
});

app.post('/signin', (req, res) => {

    let con = connectToDatabase();

    let email = req.body.email;
    let password = req.body.pws;

    let sql = `select id from users where email='${email}' and pws='${password}'`;
    
    con.query(sql, (err, ret) => {
        if (err) {
            res.send({
                'result_code': 201,
                'message': 'Sign in failed'
            });
        } else {
            if (ret.length > 0 && ret[0].id > 0) {
                res.send({
                    'result_code': 200,
                    'message': 'Sign in succeed',
                    'id': ret[0].id
                });
            } else {
                res.send({
                    'result_code': 200,
                    'message': 'User not exist',
                    'id': '-1'
                });
            }
        }
    });
});

app.post('/signup', (req, res) => {
    let con = connectToDatabase();

    let user = req.body;

    let sql = `insert into users (name,bdate,gender,email,addr,phone,pws) 
    select * from (select '${user.name}','${user.bdate}', '${user.gender}', '${user.email}', '${user.addr}', '${user.phone}', '${user.pws}') 
    as tmp where not exists (select email from users where email='${user.email}') limit 1`;
    con.query(sql, (err, ret) => {
        if (err) {
            res.send({
                'result_code': 201,
                'message': 'Sign up failed'
            });
        } else {
            let c = ret.affectedRows;
            if (c == 0) {
                res.send({
                    'result_code': 202,
                    'message': 'Email was existed...'
                });
            } 
            if (c == 1) {
                res.send({
                    'result_code': 200,
                    'message': 'Sign up succeed',
                    'id': ret.insertId
                });
            }
        }
    });
});

app.listen('8800', () => {
    console.log('Server is running in port 8800');
});

