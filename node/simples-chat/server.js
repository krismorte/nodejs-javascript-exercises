var net = require('net');

var connections = [];
var broadcast = function(message,origin){
    connections.forEach(function(connection){
        if(connection===origin)return;
        connection.write(message);
    });
};

net.createServer(function (connection){   
    connections.push(connection); 
    connection.write('Hello, Im the Server');
    connection.on('data', function (message){
        var command = message.toString();
        if(command.startsWith('/nickname')){
            console.log('achou');
            var nickname = command.replace('/nickname ','');
            broadcast(connection.nick + ' is now '+ nickname);            
            connection.nick = nickname.replace('\r','');
            console.log(connection);
            return;
        }
        broadcast(connection.nick + ' > '+ message,connection);
    });
    connection.on('end', function(){
        broadcast(connection.nick + ' has left!',connection);
        connections.splice(connections.indexOf(connection));
    });
}).listen(3000);