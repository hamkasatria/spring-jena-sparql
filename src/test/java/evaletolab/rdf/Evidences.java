package evaletolab.rdf;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import evaletolab.config.WebConfig;
import evaletolab.tool.FileUtil;
import static org.junit.Assert.*;
/**
 * Use case for evidences queries
 * - Q53	which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57	which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63	which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Evidences extends TripleStore{

	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}


	
	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS() throws Exception{
		//
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-1-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=179);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P22303"));
        
	}	

	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_disJointWith() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-2-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=179);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P22303"));
	}	
	

	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_differentFrom() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-3-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=179);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P22303"));
	}		

	/**
	 * Q57	which are located in mitochondrion with an evidence other 
	 *      than HPA and DKFZ-GFP
	 *      WARNING! term:SL-0173 is (this must be inferred) 
	 *        rdfs:sameAs term:GO:0005739;
  	 *		  rdfs:sameAs term:KW-0496; 
	 * @throws Exception 
	 */
	@Test
	public void locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception{        
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q57-locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP.sparql");
		//
		// execute query
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=239);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P09622"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P11182"));
	
	}	
	
	/**
	 * Q63 which have >=1 RRM RNA-binding domain (DO-00581 UniprotDomain) and either no GO "RNA binding" (GO:0003723 go molecular function)
	 *     other a GO "RNA binding" with evidence IEA or ISS 
	 * @throws Exception 
	 */
	@Test
	public void with1RRM_RNAbindingDomainWithEvidenceIEAorISS() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q63-with1RRM_RNAbindingDomainWithEvidenceIEAorISS.sparql");

		//
		// execute query
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=174);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q13310"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9UBU9"));
	}	


}
