const config = require('./config');
const mqtt = require ('mqtt');
const topicPublisherCommand = "unibo/frontendUserCmd";
const topicPublisherEnvironment = "unibo/environment";
const topicSubscriber = "unibo/frontendRobotState";

var client = mqtt.connect(config.mqtt_address);

var webSocket = require('./webSocketUtils');
var qaUtils = require('./qaUtils');

client.on('connect', function () {
	  client.subscribe( topicSubscriber );
	  console.log('EXPRESS - MQTT client has subscribed successfully ');
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
	var msg = message.toString();
  console.log("EXPRESS - MQTT event received");
	state = qaUtils.parseQAmessage(msg);
	webSocket.sendall(state);
});

exports.publishCommand = function( msg ){
  console.log('EXPRESS - MQTT publish command');
	client.publish(topicPublisherCommand, msg);
}

exports.publishEnvironment = function( msg ){
  console.log('EXPRESS - MQTT publish environment');
	client.publish(topicPublisherEnvironment, msg);
}