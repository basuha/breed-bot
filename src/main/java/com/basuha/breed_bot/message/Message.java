package com.basuha.breed_bot.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "msg")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "text",
//        "author"
})
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty("data")
    private String data;

    @JsonProperty("is_bot_message")
    @Column(name = "is_bot_message")
    private Boolean isBotMessage;

    @JsonProperty("type")
    private String type;

    @JsonProperty("timestamp")
    private Long timestamp;

    @Transient
    private String status;


    //
//    @JsonProperty("data")
//    private String data;

//    @JsonProperty("author")
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User author;
}