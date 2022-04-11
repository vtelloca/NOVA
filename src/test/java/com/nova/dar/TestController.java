package com.nova.dar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
	private long id;

	@BeforeEach
	public void setUp() throws Exception {

		// Reset DB
		this.mockMvc.perform(delete("/tasks").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		this.task = new MyTask("Task 1", 0);
		this.task2 = new MyTask("Task 2", 2);
		this.task3 = new MyTask("Task 3", 1);

		String reponse = this.mockMvc
				.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
						.content(objectmapper.writeValueAsString(task)).contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task2)).contentType(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(objectmapper.writeValueAsString(task3)).contentType(MediaType.APPLICATION_JSON));

		final JSONObject obj = new JSONObject(reponse);
		this.id = obj.getLong("_id");

		task.set_id(id);
		task2.set_id(id + 1);
		task3.set_id(id + 2);
	}

	@Test
	public void createTaskTest() throws Exception {

		// Create new task
		MyTask task4 = new MyTask("Task 4", 0);

		// Save task and check
		String response = this.mockMvc
				.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
						.content(objectmapper.writeValueAsString(task4)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._description").value("Task 4")).andExpect(jsonPath("$._state").value(0))
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
				// .andExpect(jsonPath("$.[0].").value(2))
				.andReturn().getResponse().getContentAsString();

		final JSONArray obj = new JSONArray(response);

		for (int i = 0; i < obj.length(); i++) {
			assertThat(obj.getJSONObject(i).getInt("_state")).isEqualTo(2);
		}

		System.out.println("CompletedTaskTest: " + response);
	}

	@Test
	public void pendingTask() throws Exception {
		String response = this.mockMvc.perform(get("/pendingTask").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// .andExpect(jsonPath("$.[0]._state").value(1))
				.andReturn().getResponse().getContentAsString();

		final JSONArray obj = new JSONArray(response);

		for (int i = 0; i < obj.length(); i++) {
			assertThat(obj.getJSONObject(i).getInt("_state")).isEqualTo(1);
		}

		System.out.println("PendingTask: " + response);
	}

	@Test
	public void getTaskById() throws Exception {
		String response = this.mockMvc
				.perform(get("/getTaskById").param("id", task.get_id().toString()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$._id").value(task.get_id())).andReturn().getResponse()
				.getContentAsString();

		System.out.println("GetTaskById: " + response);
	}

	@Test
	public void updateTask() throws Exception {

		MyTask taskmodif = new MyTask(task3.get_id(), "Task 3 modif", 0);
		// Update task3
		this.mockMvc
				.perform(put("/tasks").accept(MediaType.APPLICATION_JSON)
						.content(objectmapper.writeValueAsString(taskmodif)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		// Check task3
		String response1 = this.mockMvc
				.perform(get("/getTaskById").param("id", taskmodif.get_id().toString())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$._id").value(taskmodif.get_id())).andReturn()
				.getResponse().getContentAsString();
		System.out.println("UpdatedTask: " + response1);
	}

	@Test
	public void deleteTaskById() throws Exception {
		String response = this.mockMvc
				.perform(delete("/deleteTaskById").param("id", task.get_id().toString())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println("deleteTaskById: " + response);
	}

	@Test
	public void deleteAll() throws Exception {

		this.mockMvc.perform(delete("/tasks").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		String response = this.mockMvc.perform(get("/tasks").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[*]").isEmpty()).andReturn().getResponse()
				.getContentAsString();
		System.out.println("DeleteAll: " + response);
	}

}
