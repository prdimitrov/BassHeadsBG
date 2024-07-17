        document.addEventListener('DOMContentLoaded', function() {
            WebSocketModule.connect(); // Connect to WebSocket endpoint

            // Function to handle incoming messages and display in chat
            function handleIncomingMessage(message) {
                WebSocketModule.showMessage(message); // Call showMessage from WebSocketModule
            }

            // Example: If you receive a message from another source (not WebSocket)
            var exampleMessage = { content: "Hello, World!", sender: "Admin" };
            handleIncomingMessage(exampleMessage); // Display example message in chat
        });