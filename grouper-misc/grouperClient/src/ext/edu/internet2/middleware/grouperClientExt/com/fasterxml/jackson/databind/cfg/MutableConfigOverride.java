package edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.databind.cfg;

import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.annotation.JsonAutoDetect;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.annotation.JsonFormat;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.annotation.JsonInclude;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Extension of {@link ConfigOverride} that allows changing of
 * contained configuration settings. Exposed to
 * {@link edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.databind.Module}s that want to set
 * overrides, but not exposed to functionality that wants to apply
 * overrides.
 *
 * @since 2.8
 */
public class MutableConfigOverride
    extends ConfigOverride
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    public MutableConfigOverride() { super(); }

    protected MutableConfigOverride(MutableConfigOverride src) {
        super(src);
    }

    public MutableConfigOverride copy() {
        return new MutableConfigOverride(this);
    }

    public MutableConfigOverride setFormat(JsonFormat.Value v) {
        _format = v;
        return this;
    }

    /**
     * Override inclusion setting for all properties contained in POJOs of the
     * associated type.
     *
     * @param v Inclusion setting to apply contained properties.
     */
    public MutableConfigOverride setInclude(JsonInclude.Value v) {
        _include = v;
        return this;
    }

    /**
     * Override inclusion setting for properties of the associated type
     * regardless of the type of the POJO containing it.
     *
     * @param v Inclusion setting to apply for properties of associated type.
     *
     * @since 2.9
     */
    public MutableConfigOverride setIncludeAsProperty(JsonInclude.Value v) {
        _includeAsProperty = v;
        return this;
    }

    public MutableConfigOverride setIgnorals(JsonIgnoreProperties.Value v) {
        _ignorals = v;
        return this;
    }

    public MutableConfigOverride setIsIgnoredType(Boolean v) {
        _isIgnoredType = v;
        return this;
    }

    /**
     * @since 2.9
     */
    public MutableConfigOverride setSetterInfo(JsonSetter.Value v) {
        _setterInfo = v;
        return this;
    }

    /**
     * @since 2.9
     */
    public MutableConfigOverride setVisibility(JsonAutoDetect.Value v) {
        _visibility = v;
        return this;
    }

    /**
     * @since 2.9
     */
    public MutableConfigOverride setMergeable(Boolean v) {
        _mergeable = v;
        return this;
    }
}
