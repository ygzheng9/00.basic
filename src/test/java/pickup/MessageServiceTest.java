package pickup;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by YongGang on 2016/10/21.
 */
public class MessageServiceTest {
    private MessageService messageService;

    @Before
    public void setUp() {
        messageService = new MessageService();
    }

    @Test
    public void getMessage_ShouldReturnMessage() {
        Assert.assertEquals("Hello world.", messageService.getMessage());
    }
}
