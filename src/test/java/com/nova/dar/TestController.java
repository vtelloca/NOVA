package com.nova.dar;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@AutoConfigureMockMvc(printOnlyOnFailure = true)
@RunWith(SpringRunner.class)
@SpringBootTest

public class TestController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectmapper;

	@TestConfiguration
	static class TestConfigurationApp {
		@Bean
		ObjectMapper objectMapperPrettyPrinting() {
			return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		}
	}
	
	private MyTask task;
	private MyTask task2;
	private MyTask task3;
	private final long ID = 1;

	//@BeforeEach
	public void setUp() throws Exception {
		
		//Reset DB
		this.mockMvc.perform(delete("/tasks").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		this.task = new MyTask(ID, "Task 1", 0);
		this.task2 = new MyTask(ID+1, "Task 2", 2);
		this.task3 = new MyTask(ID+2, "Task 3", 1);
		
		this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task))
				.contentType(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task2))
				.contentType(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task3))
				.contentType(MediaType.APPLICATION_JSON));
		
	}

	@Test
	public void createTaskTest() throws Exception {

		MyTask task4 = new MyTask("Task 4", 0);	
		
		String response = this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		System.out.println("CreateTaskTest: " + response);
	}

	@Test
	public void getAllTaskTest() throws Exception {
		String response = this.mockMvc.perform(get("/tasks").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println("GetAllTaskTest: " + response);
	}
	
	@Test
	public void completedTaskTest() throws Exception {
		String response = this.mockMvc.perform(get("/completedTask").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$.state", is(2)))
				.andReturn().getResponse().getContentAsString();
		System.out.println("CompletedTaskTest: " + response);
	}
	
	@Test
	public void pendingTask() throws Exception {
		String response = this.mockMvc.perform(get("/pendingTask").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$._state", is(task3.get_state())))
				.andReturn().getResponse().getContentAsString();
		System.out.println("PendingTask: " + response);
	}
	
	@Test
	public void getTaskById() throws Exception {
		String response = this.mockMvc.perform(get("/getTaskById?id={Id}", task.get_id()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$._id", is(task.get_id())))
				.andReturn().getResponse().getContentAsString();
		System.out.println("GetTaskById: " + response);
		
	}
	
	//@Test
	public void deleteAll() throws Exception {
		String response = this.mockMvc.perform(delete("/tasks").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println("DeleteAll: " + response);
	}
	

}
