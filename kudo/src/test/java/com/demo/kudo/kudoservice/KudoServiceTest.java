package com.demo.kudo.kudoservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.kudo.dao.KudosDAO;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;
import com.demo.kudo.service.KudosService;

@RunWith(MockitoJUnitRunner.class)
public class KudoServiceTest {
	
	@InjectMocks
	private KudosService service;
	
	@Mock
	private KudosDAO dao;

	 @Test
	 public void getAllKudosTest() throws Exception{

		List<Kudos> kudosList = new ArrayList<Kudos>();
		User user =new User(1, "user1", "pass1");
		
	    Kudos kudo1=new Kudos(1, "kudo1",user );
	    Kudos kudo2=new Kudos(2, "kudo2",new User(2, "user2", "pass2"));
	    Kudos kudo3=new Kudos(3, "kudo3",new User(3, "user3", "pass3"));
	    
	    kudosList.add(kudo1);
	    kudosList.add(kudo2);
	    kudosList.add(kudo3);
	    
	    when(dao.getKudos()).thenReturn(kudosList);
         
        //test
        List<Kudos> result = service.getKudos();
         
        assertEquals(3, result.size());
        verify(dao, times(1)).getKudos();
    
    }
	 
	 @Test
	 public void getAKudoTest()
	 {
    	int id= 1;
        Kudos kudo = new Kudos(1, "kudo1", new User(1, "user1", "pass1"));
        
 	   	when(dao.getKudos(id)).thenReturn(kudo);
 	   
        Kudos theResult =service.getKudos(id);
        assertEquals("kudo1",theResult.getContent());
        }
 
    @Test
    public void saveKudoTest()
    {
        Kudos kudo =new Kudos(1, "kudo1", new User(1, "user1", "pass1"));
        service.saveKudos(kudo);	         
        verify(dao, times(1)).saveKudos(kudo);
    }
}
