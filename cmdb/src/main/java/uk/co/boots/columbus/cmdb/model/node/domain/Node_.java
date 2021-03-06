package uk.co.boots.columbus.cmdb.model.node.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import uk.co.boots.columbus.cmdb.model.environment.domain.SubEnvironment;

public class Node_ {
    // Raw attributes
    public static volatile SingularAttribute<Node, Long> id;
    public static volatile SingularAttribute<Node, NodeType> nodeType; 
    
    // One to Many
    public static volatile ListAttribute<Node, NodeIP> nodeIPs;
    public static volatile ListAttribute<Node, NodeRelationship> relationships;
    
    // Many to Many
    public static volatile SetAttribute<Node, SubEnvironment> subEnvironments;
}
