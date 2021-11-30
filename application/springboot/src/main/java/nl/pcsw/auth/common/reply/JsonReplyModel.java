package nl.pcsw.auth.common.reply;

/**
 * This is the base class for all DTO objects.
 * Within this object we can put error messages and so on which should be displayed on screen.
 *
 * @param valid: If the reply contains errors
 * @param error: If reply contains error, specified here otherwise null
 * @param model: DTO representation of the reply.
 */
public record JsonReplyModel<T>(boolean valid, JsonError error, T model) {
}
