import socket
import threading
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from kivy.uix.label import Label

UDP_IP = "127.0.0.1"
SEND_PORT = 5006  # Porta per inviare
RECV_PORT = 5005  # Porta per ricevere

class UDPChat(BoxLayout):
    def __init__(self, **kwargs):
        super().__init__(orientation='vertical', **kwargs)
        self.sock_send = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.sock_recv = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.sock_recv.bind((UDP_IP, RECV_PORT))
        
        self.chat_display = Label(text="", size_hint_y=0.7, font_size=20)
        self.text_input = TextInput(hint_text="Scrivi qui", size_hint_y=0.2, font_size=20)
        self.send_button = Button(text="Invia", size_hint_y=0.1, font_size=20)
        self.send_button.bind(on_press=self.send_message)
        
        self.add_widget(self.chat_display)
        self.add_widget(self.text_input)
        self.add_widget(self.send_button)
        
        self.receiver_thread = threading.Thread(target=self.receive_messages, daemon=True)
        self.receiver_thread.start()
    
    def send_message(self, instance):
        message = self.text_input.text
        if message:
            self.sock_send.sendto(message.encode(), (UDP_IP, SEND_PORT))
            self.update_chat(f"Tu: {message}")
            self.text_input.text = ""
    
    def receive_messages(self):
        while True:
            data, _ = self.sock_recv.recvfrom(1024)
            self.update_chat(f"Peer: {data.decode()}")
    
    def update_chat(self, message):
        self.chat_display.text += f"\n{message}"

class UDPChatApp(App):
    def build(self):
        return UDPChat()

if __name__ == "__main__":
    UDPChatApp().run()

