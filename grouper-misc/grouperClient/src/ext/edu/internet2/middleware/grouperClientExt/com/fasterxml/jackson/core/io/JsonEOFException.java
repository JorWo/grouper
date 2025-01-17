package edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.core.io;

import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.core.JsonParseException;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.core.JsonParser;
import edu.internet2.middleware.grouperClientExt.com.fasterxml.jackson.core.JsonToken;

/**
 * Specialized {@link JsonParseException} that is thrown when end-of-input
 * is reached unexpectedly, either within token being decoded, or during
 * skipping of intervening white-space that is not between root-level
 * tokens (that is, is within JSON Object or JSON Array construct).
 *
 * @since 2.8
 */
public class JsonEOFException extends JsonParseException
{
    private static final long serialVersionUID = 1L;

    /**
     * Type of token that was being decoded, if parser had enough information
     * to recognize type (such as starting double-quote for Strings)
     */
    protected final JsonToken _token;
    
    public JsonEOFException(JsonParser p, JsonToken token, String msg) {
        super(p, msg);
        _token = token;
    }

    /**
     * Accessor for possibly available information about token that was being
     * decoded while encountering end of input.
     *
     * @return JsonToken that was being decoded while encountering end-of-input
     */
    public JsonToken getTokenBeingDecoded() {
        return _token;
    }
}
