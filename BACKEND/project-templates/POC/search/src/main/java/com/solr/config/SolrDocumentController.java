package com.solr.config;

import java.util.List;

import org.hibernate.search.mapper.orm.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;

@RestController
public class SolrDocumentController {
// @Autowired
// private SolrRepo documentRepository;
// 
// @RequestMapping("/")
// public String SpringBootSolrExample() throws SolrServerException, IOException {
//	 Employee emp = new Employee();
//	 String tt = System.currentTimeMillis()+"";
//	 emp.setId(tt);
//	 emp.setName(System.currentTimeMillis()+"");
//	 emp.setPincode("90909");
//	 SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr/EMPLOYEE").build();
//			 //HttpSolrClient.Builder("http://localhost:8983/solr/EMPLOYEE").build();
//	 SolrPingResponse resp =  client.ping();
//	 
//	 
//	 client.addBean(emp, 0);
//	 
//	 User emp1 = new User();
//	 String tt1 = System.currentTimeMillis()+"";
//	 emp1.setId(tt1);
//	 emp1.setName(System.currentTimeMillis()+"");
//	 emp1.setPincode("11111");
//	 
//	 client.addBean(emp1, 0);
//	 
//	 client.getById(tt);
//	 //client.getById("test");
//      return  client.getById(tt).toString();
// }

// @RequestMapping("/save")
// public String saveAllDocuments() {
// //Store DocumentsE
//	 Employee emp = new Employee();
//	 emp.setId(System.currentTimeMillis()+"");
//	 emp.setName(System.currentTimeMillis()+"");
//       documentRepository.save(emp, Duration.ZERO);
//       return "5 documents saved!!!";
// }
// 
// @RequestMapping("/getAll")
// public List<Employee> getAllDocs() {
//       List<Employee> documents = new ArrayList<>();
//       // iterate all documents and add it to list
//       for (Employee doc : this.documentRepository.findAll(Sort.unsorted())) {
//       documents.add(doc);
// }
// return documents;
// }

	@Autowired
	private EmployeeRepo repo;

	@RequestMapping("/")
	public Employee SpringBootSolrExample() {
		Employee emp = new Employee();
		emp.setId(System.currentTimeMillis() + "");
		emp.setName("சோதனை விண்ணப்பம்");
		emp.setPincode("pincode");
		return repo.save(emp);
	}

	@Autowired
	private EntityManager entitymanager;

	@RequestMapping("/{value}")
	public List<Employee> SpringBootSolrExample(@PathVariable("value") String value) {
		
		List<Employee> emp = Search.session(entitymanager).search(Employee.class)
		.where(field -> field.wildcard().fields("name").matching(value)).fetch(10).hits();
		
		return Search.session(entitymanager).search(Employee.class)
				.where(field -> field.match().fields("name").matching(value).fuzzy()).fetch(10).hits();

	}

}