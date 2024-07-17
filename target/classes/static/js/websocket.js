        document.addEventListener('DOMContentLoaded', function() {
            WebSocketModule.connect(); // Connecting to WebSocket endpoint

            // Function that handles incoming messages and display in chat
            function handleIncomingMessage(message) {
                WebSocketModule.showMessage(message); // Call showMessage from WebSocketModule
            }

            var exampleMessage = { content: "🤯BassHeadsBG🤯", sender: "Admin" };
            handleIncomingMessage(exampleMessage); // Display message in chat
        });