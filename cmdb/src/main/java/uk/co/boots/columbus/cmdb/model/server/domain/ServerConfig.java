package uk.co.boots.columbus.cmdb.model.server.domain;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.google.common.base.MoreObjects;

import uk.co.boots.columbus.cmdb.model.core.domain.Identifiable;
import uk.co.boots.columbus.cmdb.model.core.domain.IdentifiableHashBuilder;

@Entity
@Table(name = "cm_serverconfig")
public class ServerConfig implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ServerConfig.class.getName());

    // Raw attributes
    @Column(name = "ServConfigID", precision = 19)
    @GeneratedValue
    @Id
    private Long id;

    @Size(max = 50)
    @Column(name = "ServConfigParameter", length = 50)
    private String parameter;

    @Size(max = 50)
    @Column(name = "ServConfigValue", length = 50)
    private String value;

    @Size(max = 45)
    @Column(name = "ServConfigHieraAddress", length = 45)
    private String hieraAddress;

    //@JoinColumn(name = "NodeID")
    @JoinColumn(name = "ServerID")
    @ManyToOne
    private Server server;

    @Override
    public String entityClassName() {
        return ServerConfig.class.getSimpleName();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ServerConfig id(Long id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [parameter] ------------------------

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ServerConfig parameter(String parameter) {
        setParameter(parameter);
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ServerConfig value(String value) {
        setValue(value);
        return this;
    }

    public String getHieraAddress() {
        return hieraAddress;
    }

    public void setHieraAddress(String hieraAddress) {
        this.hieraAddress = hieraAddress;
    }

    public ServerConfig hieraAddress(String hieraAddress) {
        setHieraAddress(hieraAddress);
        return this;
    }

    /**
     * Set the {@link #server} without adding this ServerConfig instance on the passed {@link #server}
     */
    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public ServerConfig server(Server server) {
        setServer(server);
        return this;
    }

    /**
     * Apply the default values.
     */
    public ServerConfig withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof ServerConfig && hashCode() == other.hashCode());
    }

    @Transient
    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this, parameter + value + hieraAddress);
    }

    /**
     * Construct a readable string representation for this ServerConfig instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("parameter", getParameter()) //
                .add("value", getValue()) //
                .add("hieraAddress", getHieraAddress()) //
                .toString();
    }
}