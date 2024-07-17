document.addEventListener('DOMContentLoaded', function() {
    var chatIcon = document.getElementById('chat-icon');
    var chatBox = document.getElementById('chat-box');
    var chatInput = document.getElementById('chat-input');
    var chatContent = document.getElementById('chat-content');
    var closeButton = document.getElementById('close-chat-btn');

    var stompClient = null;

    chatIcon.addEventListener('click', function(event) {
        event.stopPropagation(); // Prevent event from bubbling up
        toggleChatBox();
    });

    closeButton.addEventListener('click', function() {
        closeChatBox();
    });

    document.addEventListener('click', function(event) {
        var isClickInsideChat = chatBox.contains(event.target);
        var isClickOnChatIcon = (event.target === chatIcon || chatIcon.contains(event.target));

        if (!isClickInsideChat && !isClickOnChatIcon) {
            closeChatBox();
        }
    });

    chatInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            var content = chatInput.value.trim();
            if (content !== '') {
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({'content': content}));
                chatInput.value = '';
            }
        }
    });

    // Connect to WebSocket endpoint
    var socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // Subscribe to topic for receiving new messages
        stompClient.subscribe('/topic/public', function(message) {
            var msg = JSON.parse(message.body);
            showMessage(msg.content);
        });

        // Subscribe to topic for initial messages
        stompClient.subscribe('/topic/public.init', function(messages) {
            var messageList = JSON.parse(messages.body);
            messageList.forEach(function(message) {
                showMessage(message.content);
            });
        });

        // Fetch initial messages on connection
        stompClient.send("/app/chat.loadMessages", {}, {});
    });

    function showMessage(message) {
        var messageElement = document.createElement('div');
        messageElement.textContent = message; // Use textContent to prevent XSS vulnerability
        chatContent.appendChild(messageElement);
        chatContent.scrollTop = chatContent.scrollHeight;
    }

    function toggleChatBox() {
        if (chatBox.classList.contains('chat-open')) {
            closeChatBox();
        } else {
            openChatBox();
        }
    }

    function openChatBox() {
        chatBox.classList.remove('chat-closed');
        chatBox.classList.add('chat-open');
    }

    function closeChatBox() {
        chatBox.classList.remove('chat-open');
        chatBox.classList.add('chat-closed');
    }
});