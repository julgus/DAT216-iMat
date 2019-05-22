package tests;
import backend.FilesBackend;
import model.Profile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import tests.MockModels.FilesBackendMock;

import java.util.Arrays;

public class FilesBackendTest {
    @Test
    public void saveLoadProfileEqual(){
        final var mock = getMockProfile();
        final var fbMock = new FilesBackendMock();
        fbMock.saveProfile(mock);
        final var readFrom = fbMock.readProfileFromFile();

        Arrays.stream(Profile.class.getMethods())
                .filter(x -> x.getName().toLowerCase().startsWith("get"))
                .forEach(x -> {
                    try {
                        var method = Profile.class.getMethod(x.getName(), null);
                        Assert.assertEquals(method.invoke(mock), method.invoke(readFrom));
                    } catch (Exception e) {
                        throw new AssertionError("Failed with reflections: " + e);
                    }
                });
    }

    @Test
    public void saveLoadProfileNotEqual(){
        final var mock = getMockProfile();
        final var fbMock = new FilesBackendMock();
        fbMock.saveProfile(mock);
        final var readFrom = fbMock.readProfileFromFile();
        readFrom.setFirstName("Not" + mock.getFirstName());

        Assert.assertThrows(AssertionError.class, () ->
            Arrays.stream(Profile.class.getMethods())
                    .filter(x -> x.getName().toLowerCase().startsWith("get"))
                    .forEach(x -> {
                        try {
                            var method = Profile.class.getMethod(x.getName(), null);
                            Assert.assertEquals(method.invoke(mock), method.invoke(readFrom));
                        } catch (Exception e) {
                            throw new AssertionError("Failed with reflections: " + e);
                        }
                    })
        );
    }

    private Profile getMockProfile(){
        var p = new Profile();
        p.setFirstName("TestFirstName");
        p.setLastName("TestLastName");
        p.setMobilePhoneNumber("1234567890");
        p.setEmail("a.b@c.d");
        p.setAddress("g 1 v 2");
        p.setPostCode("12345");
        p.setCity("TestCity");
        p.setHouse(true);
        p.setLevel(0);
        p.setCardType("TestCardType");
        p.setHoldersName(String.format("%s %s", p.getFirstName(), p.getLastName()));
        p.setCardNumber("1234567890");
        p.setCvcCode(12345);
        p.setCardPayment(true);
        return p;
    }
}
