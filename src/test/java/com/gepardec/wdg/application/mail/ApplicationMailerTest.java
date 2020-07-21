package com.gepardec.wdg.application.mail;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@QuarkusTest
public class ApplicationMailerTest {

    @ConfigProperty(name = "ApplicationMailer.Default")
    private String DEFAULT_ADR;

    @Inject
    MockMailbox mailbox;

    @BeforeEach
    void init() {
        mailbox.clear();
    }

    @Test
    void test_sendOneMail_OK() throws MessagingException, IOException {

        given()
                .when()
                .get("/sendDefaultMail")
                .then()
                .statusCode(Response.Status.ACCEPTED.getStatusCode())
                .body(any(String.class));

        List<Mail> sent = mailbox.getMessagesSentTo(DEFAULT_ADR);
        assertThat(sent, hasSize(1));

    }

}