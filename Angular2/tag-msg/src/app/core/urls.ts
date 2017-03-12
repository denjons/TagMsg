


const baseUrl = "http://localhost:8080/tm/rest/";

const getEligibleRequests = baseUrl + "request/getEligibleRequestsForUser";
const getResponsesForRequest = baseUrl + "response/getResponsesForRequest";
const getRequestsForUser = baseUrl + "request/getRequestsForUser";

export class TMURLS{

    static getEligibleRequestsURL(limit: number, offset: number){
        var result = getEligibleRequests + "?";
        result = this.addPathParam(result, "limit", limit);
        result = this.addPathParam(result, "offset", offset);
        return result;
    }

    static getRequestsForUser(limit: number, offset: number){
        var result = getRequestsForUser + "?";
        result = this.addPathParam(result, "limit", limit);
        result = this.addPathParam(result, "offset", offset);
        return result;
    }

    static getResponses(limit: number, offset: number, request: string) {
        var result = getResponsesForRequest + "?";
        result = this.addPathParam(result, "limit", limit);
        result = this.addPathParam(result, "offset", offset);
        result = this.addPathParam(result, "request", request);
        return result;
    }

    private static addPathParam(url : string, name: string, param){
        if(param == "" || param == null){
            return url;
        }
        if(url.slice(-1) != "?"){
            url = url + "&";
        }
        return url + name +"="+ param;
    }

}

