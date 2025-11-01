package org.example.restapi.testcases;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.example.restapi.models.User;
import org.example.restapi.services.UserService;
import org.testng.Assert;

import java.util.Map;

public class UserTests {

    private final UserService userService;
    private User user;
    private Response response;
    Map<String,String> map;
    private int userId;
    private String token;
    public UserTests()
    {
        this.userService = new UserService();
    }
    @Given("user is registering")
    public void userIsRegistering(DataTable data)
    {
        map = data.asMap(String.class,String.class);
        user = new User(map.get("name"),map.get("phone"),map.get("email"),map.get("password"));
        response = userService.postRequest("/api/users/register",user);
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
        userId = response.jsonPath().get("id");
        System.out.println(userId);
    }
    @When("the user is successfully registered")
    public void theUserIsSuccessfullyRegistered()
    {
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.getBody().path("name"),map.get("name"));
    }
    @Then("the user can login")
    public void theUserCanLogin()
    {
        user = new User(map.get("email"),map.get("password"));
        response = userService.postRequest1("/api/users/login",user);
        token = response.jsonPath().getString("token");
        System.out.println("token:" + token);
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
    }
    @Given("the user can login with {string} and {string}")
    public void theUserCanLoginWith(String email,String password)
    {
        user = new User(email,password);
        response = userService.postRequest1("/api/users/login",user);
        token = response.jsonPath().getString("token");
        System.out.println("token:" + token);
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
    }
    @Then("token is generated successfully")
    public void tokenIsGeneratedSuccessfully()
    {
        Assert.assertTrue(token!=null,"Token is not generated successfully");
    }
}
