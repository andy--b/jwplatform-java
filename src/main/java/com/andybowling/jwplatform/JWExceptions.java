package com.andybowling.jwplatform.jwexceptions;

public class JWExceptions
{
    public static void mapJWException(String errorType, String message) throws JWPlatformException
    {
        // TODO: a cleaner way to do this (a map? something that concatenates strings to make class name?)
        switch (errorType)
        {
            case "NotFound":
                throw new JWPlatformNotFoundException(message);
            case "NoMethod":
                throw new JWPlatformNoMethodException(message);
            case "NotImplemented":
                throw new JWPlatformNotImplementedException(message);
            case "NotSupported":
                throw new JWPlatformNotSupportedException(message);
            case "CallFailed":
                throw new JWPlatformCallFailedException(message);
            case "CallUnavailable":
                throw new JWPlatformCallUnavailableException(message);
            case "CallInvalid":
                throw new JWPlatformCallInvalidException(message);
            case "ParameterMissing":
                throw new JWPlatformParameterMissingException(message);
            case "ParameterEmpty":
                throw new JWPlatformParameterEmptyException(message);
            case "ParameterEncoding":
                throw new JWPlatformParameterEncodingException(message);
            case "ParameterInvalid":
                throw new JWPlatformParameterInvalidException(message);
            case "PreconditionFailed":
                throw new JWPlatformPreconditionFailedException(message);
            case "ItemAlreadyExists":
                throw new JWPlatformItemAlreadyExistsException(message);
            case "PermissionDenied":
                throw new JWPlatformPermissionDeniedException(message);
            case "Database":
                throw new JWPlatformDatabaseException(message);
            case "Integrity":
                throw new JWPlatformIntegrityException(message);
            case "DigestMissing":
                throw new JWPlatformDigestMissingException(message);
            case "DigestInvalid":
                throw new JWPlatformDigestInvalidException(message);
            case "FileUploadFailed":
                throw new JWPlatformFileUploadFailedException(message);
            case "FileSizeMissing":
                throw new JWPlatformFileSizeMissingException(message);
            case "FileSizeInvalid":
                throw new JWPlatformFileSizeInvalidException(message);
            case "Internal":
                throw new JWPlatformInternalException(message);
            case "ApiKeyMissing":
                throw new JWPlatformApiKeyMissingException(message);
            case "ApiKeyInvalid":
                throw new JWPlatformApiKeyInvalidException(message);
            case "TimestampMissing":
                throw new JWPlatformTimestampMissingException(message);
            case "TimestampInvalid":
                throw new JWPlatformTimestampInvalidException(message);
            case "NonceInvalid":
                throw new JWPlatformNonceInvalidException(message);
            case "SignatureMissingEr":
                throw new JWPlatformSignatureMissingErException(message);
            case "SignatureInvalid":
                throw new JWPlatformSignatureInvalidException(message);
            case "RateLimitExceeded":
                throw new JWPlatformRateLimitExceededException(message);
            default:
                throw new JWPlatformUnknownException(message);
        }
    }

    public static class JWPlatformException extends Exception
    {
        public JWPlatformException() {;}
        public JWPlatformException(String message) { super(message); }
    }

    public static class JWPlatformUnknownException extends JWPlatformException
    {
        public JWPlatformUnknownException() {;}
        public JWPlatformUnknownException(String message) { super(message); }
    }

    public static class JWPlatformNotFoundException extends JWPlatformException
    {
        public JWPlatformNotFoundException() {;}
        public JWPlatformNotFoundException(String message) { super(message); }
    }

    public static class JWPlatformNoMethodException extends JWPlatformException
    {
        public JWPlatformNoMethodException() {;}
        public JWPlatformNoMethodException(String message) { super(message); }
    }

    public static class JWPlatformNotImplementedException extends JWPlatformException
    {
        public JWPlatformNotImplementedException() {;}
        public JWPlatformNotImplementedException(String message) { super(message); }
    }

    public static class JWPlatformNotSupportedException extends JWPlatformException
    {
        public JWPlatformNotSupportedException() {;}
        public JWPlatformNotSupportedException(String message) { super(message); }
    }

    public static class JWPlatformCallFailedException extends JWPlatformException
    {
        public JWPlatformCallFailedException() {;}
        public JWPlatformCallFailedException(String message) { super(message); }
    }

    public static class JWPlatformCallUnavailableException extends JWPlatformException
    {
        public JWPlatformCallUnavailableException() {;}
        public JWPlatformCallUnavailableException(String message) { super(message); }
    }

    public static class JWPlatformCallInvalidException extends JWPlatformException
    {
        public JWPlatformCallInvalidException() {;}
        public JWPlatformCallInvalidException(String message) { super(message); }
    }

    public static class JWPlatformParameterMissingException extends JWPlatformException
    {
        public JWPlatformParameterMissingException() {;}
        public JWPlatformParameterMissingException(String message) { super(message); }
    }

    public static class JWPlatformParameterEmptyException extends JWPlatformException
    {
        public JWPlatformParameterEmptyException() {;}
        public JWPlatformParameterEmptyException(String message) { super(message); }
    }

    public static class JWPlatformParameterEncodingException extends JWPlatformException
    {
        public JWPlatformParameterEncodingException() {;}
        public JWPlatformParameterEncodingException(String message) { super(message); }
    }

    public static class JWPlatformParameterInvalidException extends JWPlatformException
    {
        public JWPlatformParameterInvalidException() {;}
        public JWPlatformParameterInvalidException(String message) { super(message); }
    }

    public static class JWPlatformPreconditionFailedException extends JWPlatformException
    {
        public JWPlatformPreconditionFailedException() {;}
        public JWPlatformPreconditionFailedException(String message) { super(message); }
    }

    public static class JWPlatformItemAlreadyExistsException extends JWPlatformException
    {
        public JWPlatformItemAlreadyExistsException() {;}
        public JWPlatformItemAlreadyExistsException(String message) { super(message); }
    }

    public static class JWPlatformPermissionDeniedException extends JWPlatformException
    {
        public JWPlatformPermissionDeniedException() {;}
        public JWPlatformPermissionDeniedException(String message) { super(message); }
    }

    public static class JWPlatformDatabaseException extends JWPlatformException
    {
        public JWPlatformDatabaseException() {;}
        public JWPlatformDatabaseException(String message) { super(message); }
    }

    public static class JWPlatformIntegrityException extends JWPlatformException
    {
        public JWPlatformIntegrityException() {;}
        public JWPlatformIntegrityException(String message) { super(message); }
    }

    public static class JWPlatformDigestMissingException extends JWPlatformException
    {
        public JWPlatformDigestMissingException() {;}
        public JWPlatformDigestMissingException(String message) { super(message); }
    }

    public static class JWPlatformDigestInvalidException extends JWPlatformException
    {
        public JWPlatformDigestInvalidException() {;}
        public JWPlatformDigestInvalidException(String message) { super(message); }
    }

    public static class JWPlatformFileUploadFailedException extends JWPlatformException
    {
        public JWPlatformFileUploadFailedException() {;}
        public JWPlatformFileUploadFailedException(String message) { super(message); }
    }

    public static class JWPlatformFileSizeMissingException extends JWPlatformException
    {
        public JWPlatformFileSizeMissingException() {;}
        public JWPlatformFileSizeMissingException(String message) { super(message); }
    }

    public static class JWPlatformFileSizeInvalidException extends JWPlatformException
    {
        public JWPlatformFileSizeInvalidException() {;}
        public JWPlatformFileSizeInvalidException(String message) { super(message); }
    }

    public static class JWPlatformInternalException extends JWPlatformException
    {
        public JWPlatformInternalException() {;}
        public JWPlatformInternalException(String message) { super(message); }
    }

    public static class JWPlatformApiKeyMissingException extends JWPlatformException
    {
        public JWPlatformApiKeyMissingException() {;}
        public JWPlatformApiKeyMissingException(String message) { super(message); }
    }

    public static class JWPlatformApiKeyInvalidException extends JWPlatformException
    {
        public JWPlatformApiKeyInvalidException() {;}
        public JWPlatformApiKeyInvalidException(String message) { super(message); }
    }

    public static class JWPlatformTimestampMissingException extends JWPlatformException
    {
        public JWPlatformTimestampMissingException() {;}
        public JWPlatformTimestampMissingException(String message) { super(message); }
    }

    public static class JWPlatformTimestampInvalidException extends JWPlatformException
    {
        public JWPlatformTimestampInvalidException() {;}
        public JWPlatformTimestampInvalidException(String message) { super(message); }
    }

    public static class JWPlatformNonceInvalidException extends JWPlatformException
    {
        public JWPlatformNonceInvalidException() {;}
        public JWPlatformNonceInvalidException(String message) { super(message); }
    }

    public static class JWPlatformSignatureMissingErException extends JWPlatformException
    {
        public JWPlatformSignatureMissingErException() {;}
        public JWPlatformSignatureMissingErException(String message) { super(message); }
    }

    public static class JWPlatformSignatureInvalidException extends JWPlatformException
    {
        public JWPlatformSignatureInvalidException() {;}
        public JWPlatformSignatureInvalidException(String message) { super(message); }
    }

    public static class JWPlatformRateLimitExceededException extends JWPlatformException
    {
        public JWPlatformRateLimitExceededException() {;}
        public JWPlatformRateLimitExceededException(String message) { super(message); }
    }

}
