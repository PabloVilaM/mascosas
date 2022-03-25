import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.Id;
import discord4j.discordjson.json.*;
import discord4j.discordjson.possible.Possible;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(final String[] args) {
        //https://discord.com/api/oauth2/authorize?client_id=794212735118082069&scope=bot Este es el link para añadir mi bot a un servidor.
        //https://discord.com/api/oauth2/authorize?client_id=953628241518821426&scope=bot
        // Es importante que tenga permisos en dicho servidor porque si no no lo podría meter (por cuestiones de seguridad obvias).
        final String token = "OTUzNjI4MjQxNTE4ODIxNDI2.YjHVgg.TwFgo7V3Vz1VijFKjNKCQXV1mSg";
        //Aqui tendriamos nuestro token. Dicho token se saca creando una aplicación en la página de Developer de Discord, obviamente te tienes que loggear.
        // Dicha aplicación te permitira crear el bot en ella. Comentar tambien que te proporciona id de Aplicación, como una id pública y de cliente. (Aparte del token claro
        final DiscordClient client = DiscordClient.create(token); //Creamos un cliente de Discord (para el bot).
        final GatewayDiscordClient gateway = client.login().block(); //Creamos el gateaway para que dicho cliente se logee.

        gateway.on(MessageCreateEvent.class).subscribe(event -> { //Es una expresión lambda, que utiliza un evento de mensaje para posteriormente trabajr con ello.
            final Message message = event.getMessage(); //Aqui lee los mensajes escritos en los canales. Es un programa muy simple dado que no filtra por canal ni por usuario
            final MessageChannel channel = message.getChannel().block();
            if ("!ping".equals(message.getContent())) { // Método simple que si el mensaje equivale a un !ping, el bot devuelva un !pong en su mismo canal el cual anteriormente ha creado.
                channel.createMessage("Pong!").block();


            }
            if(message.getContent().startsWith("!suggest")){
                UserData data = message.getUserData();
               String nombre =  data.username();
               Optional<String> opt = Optional.of(":fire:");
               User usuario = new User(gateway,data);
               String avatarUrl = usuario.getAvatarUrl();


               String id = usuario.getId().toString();
               String idrecor = id.replace("Snowflake{", "");
               String idrecor2 = idrecor    .replace("}","");
               double rand = Math.random()*1000000000;
               long randLong = (long)rand;
               String sugerencia = message.getContent().replace("!suggest", "");

                 String IMAGE_URL = "https://cdn.betterttv.net/emote/55028cd2135896936880fdd7/3x";
                  String ANY_URL = "https://www.youtube.com/watch?v=5zwY50-necw";
                EmbedCreateSpec.Builder builder =  EmbedCreateSpec.builder();
                //builder.author("Proponedor", null , null);
                //builder.image(avatarUrl);
                builder.color(Color.WHITE);
                //builder.title("setTitle/setUrl");
                //builder.url(ANY_URL);
                //builder.description("setDescription\n" +
                  //      "big D: is setImage\n" +
                    ////  "<-- setColor");
                builder.addField("Proponedor", nombre +"#" + usuario.getDiscriminator(), true);
                builder.addField("Propuesta", sugerencia, false);
                builder.thumbnail(avatarUrl);
                builder.footer("Usuario id: " + idrecor2 + "  sID:" + randLong, null);
                builder.timestamp(Instant.now());

                channel.createMessage(builder.build()).block();

            }

            if(message.getContent().startsWith("!approve")){

            }
        });


        gateway.onDisconnect().block(); //Cuando se desconecta del gateaway bloquea al final el bot
    }


}
