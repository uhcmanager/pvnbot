package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class RollTest {

    @Test
    public void onCommand() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getAsMention()).thenReturn("#420");

        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getEffectiveName()).thenReturn("420Blaze");

        MessageChannel channel = Mockito.mock(MessageChannel.class);
        MessageAction action = Mockito.mock(MessageAction.class);
        Mockito.when(channel.sendMessage((MessageEmbed) Mockito.any())).thenReturn(action);

        MessageReceivedEvent e = Mockito.mock(MessageReceivedEvent.class);
        Mockito.when(e.getAuthor()).thenReturn(user);
        Mockito.when(e.getMember()).thenReturn(member);
        Mockito.when(e.getChannel()).thenReturn(channel);

        Roll test = new Roll();
        test.onCommand(new String[0], e);
    }
}