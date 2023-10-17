/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.websockets;

import com.nimbusds.jwt.JWTParser;
import com.sample.exceptions.AuthenticationFailedException;
import com.sample.mappers.NotificationMapper;
import com.sample.models.responses.NotificationResponse;
import com.sample.services.NotificationService;
import com.sample.services.UserService;
import com.sample.utils.consts.NotificationCode;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.sample.repositories.entities.Notification;
import com.sample.repositories.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerWebSocket("/ws/notification")
@Tag(name = "Notification Web Socket")
@Controller
@RequiredArgsConstructor
public class NotificationWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(NotificationWebSocket.class);

    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    private final UserService userService;

    private String getEmail(String jwt) throws ParseException {
        if (StringUtils.isEmpty(jwt)) throw new AuthenticationFailedException();
        return JWTParser.parse(jwt).getJWTClaimsSet().getSubject();
    }

    @OnOpen
    public Publisher<List<NotificationResponse>> onOpen(
            WebSocketSession session, @QueryValue(defaultValue = "") String token)
            throws ParseException {
        String email = null;
        if (!StringUtils.isEmpty(token)) email = getEmail(token);
        if (email == null)
            email =
                    session.getUserPrincipal()
                            .orElseThrow(() -> new AuthenticationFailedException())
                            .getName();
        logger.debug("{} has been connected with session id {}", email, session.getId());
        return session.send(
                notificationService.findUnreadNotification(
                        userService.findUserByEmailOrDie(email)));
    }

    @OnMessage
    public Publisher<List<NotificationResponse>> onMessage(
            String message, @QueryValue(defaultValue = "") String token, WebSocketSession session)
            throws ParseException {
        String email = null;
        if (!StringUtils.isEmpty(token)) email = getEmail(token);
        if (email == null)
            email =
                    session.getUserPrincipal()
                            .orElseThrow(() -> new AuthenticationFailedException())
                            .getName();
        User user = userService.findUserByEmailOrDie(email);
        if (message.equals(NotificationCode.clearMessages)) {
            notificationService.makeNotificationsRead(user);
        } else if (message.equals(NotificationCode.checkMessages)) {
            return session.send(notificationService.findUnreadNotification(user));
        }
        return session.send(Collections.emptyList());
    }

    List<NotificationResponse> fromList(List<Notification> notifications) {
        if (notifications == null) return Collections.emptyList();
        return notifications.stream()
                .map(notificationMapper::notificationToNotificationResponse)
                .collect(Collectors.toList());
    }
}
