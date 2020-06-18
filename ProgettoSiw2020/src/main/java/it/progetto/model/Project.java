package it.progetto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;




@Entity
@Table(name = "projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
	private String description; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	
	@ManyToMany
	private List<User> members;
	
	@OneToMany(fetch = FetchType.EAGER,
			   cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id")
	private List<Task> tasks;
	
	
	public Project() {
		
		this.members = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}
	
	public Project(String name, String description) {
		
		this ();
		this.name = name;
		this.description = description;
	}
	
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addMember(User member) {
		this.members.add(member);
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

	
	
	
	
	
}
