package ru.shishlov.btf.controllerTest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.shishlov.btf.dto.MessageDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


public class MessageControllerTest extends DialogControllerTest{

    //will create new dialog for Leo and Leo1 and then try to send and receive a message
    static final String WEBSOCKET_URI = "ws://localhost:8080/ws";

   protected BlockingQueue<MessageDto> blockingQueue;
   protected WebSocketStompClient stompClient;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        //Leo and Leo1 are already created, so let's create dialog
        super.shouldCorrectCreateNewDialog();
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));

        //it will automatically deleted with users, so we can start chatting
    }

    @Test
    public void shouldCorrectSendAndReceiveAMessage() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe("/user/Leo1/chat", new CreateMessageStompFrameHandler());

        MessageDto messageDto = new MessageDto();
        messageDto.setLoginFrom("Leo");
        messageDto.setText("Hello Leo1");
        messageDto.setDialogId(dialogId);

        String input = mapToJson(messageDto);
    // ok now we can send message with post request with Leo and try to receive it by Leo1
        MvcResult mvcc = mvc.perform(MockMvcRequestBuilders.post("/dialog/" + dialogId+"/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, token).content(input)).andReturn();

        assertEquals(200, mvcc.getResponse().getStatus());
        //now trying to recieve it from session
        MessageDto message = blockingQueue.poll(1, SECONDS);
        assertNotNull(message);
        assertEquals("Hello Leo1", message.getText());
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class CreateMessageStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return MessageDto.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.add((MessageDto) o);
        }
    }



}
