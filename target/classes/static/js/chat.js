var WebSocketModule = (function() {
    var stompClient = null;
    var socket = null;
    var connected = false;
    var messageQueue = [];
    var chatContent = null; // Initialize chatContent variable
    var displayedMessages = []; // Array to track displayed messages

    function connect() {
        var socketUrl = '/chat-websocket'; // WebSocket endpoint URL

        // Initialize SockJS and Stomp client
        socket = new SockJS(socketUrl);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            connected = true;

            // Subscribe to the public topic
            subscribeToPublicTopic();

            // Process queued messages
            processMessageQueue();
        }, function(error) {
            console.error('Connection error: ' + error);
            connected = false;
            setTimeout(connect, 10000); // Attempt to reconnect after 10 seconds
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
        connected = false;

        // Clear chat messages from localStorage
        localStorage.removeItem('chatMessages');
    }

    function sendMessage(destination, headers, body) {
        if (connected && stompClient !== null) {
            stompClient.send(destination, headers, body);
        } else {
            // Queue the message if not connected
            messageQueue.push({ destination: destination, headers: headers, body: body });
        }
    }

    function subscribeToPublicTopic() {
        var topic = '/topic/public';
        if (connected) {
            stompClient.subscribe(topic, function(message) {
                var msg = JSON.parse(message.body);
                showMessage(msg); // Display the message content
            });
            console.log('Subscribed to topic: ' + topic);
        } else {
            console.error('Cannot subscribe to ' + topic + ': Not connected');
        }
    }

    function processMessageQueue() {
        while (messageQueue.length > 0) {
            var message = messageQueue.shift();
            sendMessage(message.destination, message.headers, message.body); // Send queued messages
        }
    }

    function showMessage(message) {
        var messageElement = document.createElement('div');
        messageElement.textContent = message.content; // Display message content

        // Check if message is already displayed
        var messageExists = displayedMessages.some(function(msg) {
            return msg.content === message.content;
        });
        if (messageExists) {
            return; // Exit function if message is already displayed
        }

        chatContent.appendChild(messageElement); // Append message to chat display
        chatContent.scrollTop = chatContent.scrollHeight; // Scroll to bottom of chat window

        // Add message to displayedMessages array
        displayedMessages.push(message);

        // Store message in localStorage if not already stored
        var messages = JSON.parse(localStorage.getItem('chatMessages')) || [];
        messageExists = messages.some(function(msg) {
            return msg.content === message.content;
        });
        if (!messageExists) {
            messages.push(message);
            localStorage.setItem('chatMessages', JSON.stringify(messages));
        }
    }

    // Initialize WebSocket connection when DOM is fully loaded
    document.addEventListener('DOMContentLoaded', function() {
        chatContent = document.getElementById('chat-content'); // Initialize chatContent here
        if (!chatContent) {
            console.error('chat-content element not found.');
            return;
        }

        connect(); // Establish WebSocket connection

        // Load messages from localStorage and display them
        var storedMessages = JSON.parse(localStorage.getItem('chatMessages')) || [];

        storedMessages.forEach(function(message) {
            // Display only unique messages
            var messageExists = displayedMessages.some(function(msg) {
                return msg.content === message.content;
            });
            if (!messageExists) {
                showMessage(message);
                displayedMessages.push(message);
            }
        });

        // Event listener for chat input
        var chatInput = document.getElementById('chat-input');
        chatInput.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                var content = chatInput.value.trim();
                if (content !== '') {
                    var uniqueId = new Date().getTime(); // Generate a unique ID for the message
                    sendMessage("/app/chat.sendMessage", {}, JSON.stringify({'content': content, 'id': uniqueId}));
                    chatInput.value = '';
                }
            }
        });

        // Event listener for chat icon toggle
        var chatIcon = document.getElementById('chat-icon');
        chatIcon.addEventListener('click', function(event) {
            event.stopPropagation();
            toggleChatBox();
        });

        // Event listener for closing chat box
        var closeButton = document.getElementById('close-chat-btn');
        closeButton.addEventListener('click', function() {
            closeChatBox();
        });

        // Event listener to close chat box on outside click
        document.addEventListener('click', function(event) {
            var chatBox = document.getElementById('chat-box');
            var isClickInsideChat = chatBox.contains(event.target);
            var isClickOnChatIcon = (event.target === chatIcon || chatIcon.contains(event.target));

            if (!isClickInsideChat && !isClickOnChatIcon) {
                closeChatBox();
            }
        });
    });

    function toggleChatBox() {
        var chatBox = document.getElementById('chat-box');
        if (chatBox.classList.contains('chat-open')) {
            closeChatBox();
        } else {
            openChatBox();
        }
    }

    function openChatBox() {
        var chatBox = document.getElementById('chat-box');
        chatBox.classList.remove('chat-closed');
        chatBox.classList.add('chat-open');
    }

    function closeChatBox() {
        var chatBox = document.getElementById('chat-box');
        chatBox.classList.remove('chat-open');
        chatBox.classList.add('chat-closed');
    }

    return {
        connect: connect,
        disconnect: disconnect,
        sendMessage: sendMessage,
        showMessage: showMessage
    };
})();
