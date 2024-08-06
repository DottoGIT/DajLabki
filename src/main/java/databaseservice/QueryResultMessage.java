package databaseservice;

public class QueryResultMessage {
    ResultCode result = ResultCode.DONE;

    public String GenerateErrorMessage(String operationDescription)
    {
        switch(result)
        {
            case DONE: return operationDescription + " przebiegło pomyślnie";
            case INVALID_DATA: return operationDescription + " zawiera błędne dane";
            case NO_DATA_FOUND: return "brak wyników dla operacji '" + operationDescription + "'";
            case NO_CONNECTION: return "Utrata połączenia z bazą podczas operacji '" + operationDescription + "'";
            default: return "Nieznany błąd podczas operacji '" + operationDescription + "'";
        }
    }
}
