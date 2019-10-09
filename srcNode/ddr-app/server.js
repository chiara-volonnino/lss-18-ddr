const portNumber = readPortNumberFromArguments();

const server = require('./express/index');
server.config();

server.app.use(server.express.static(__dirname + '/dist/ddr-app'));
server.app.get('/*', (req, res) => res.sendFile(server.path.join(__dirname)));

server.launch(portNumber);

function readPortNumberFromArguments() {
  const port = Number(process.argv[2])
  
  if (!port) return undefined;

  if (port < 0 || port >= 65536) {
    console.error("This script expects a valid port number (>= 0 and < 65536) as argument.")
    process.exit()
  }

  return port
}