package net.httpserver.util;

public enum StatusCode {

	//TODO: Implement more status codes.
	
	//1xx
	Continue(100),
	Switching_Protocols(101),
	Processing(102),
	
	//2xx
	OK(200),
	Created(201),
	Accepted(202),
	Non_Authoritative_Information(203),
	No_Content(204),
	Reset_Content(205),
	Partial_Content(206),
	Multi_Status(207),
	Already_Reported(208),
	IM_Used(226),
	
	//3xx
	Multiple_Choices(300),
	Moved_Permanently(301),
	Found(302),
	See_Other(303),
	Not_Modified(304),
	Use_Proxy(305),
	Switch_Proxy(306),
	Temporary_Redirect(307),
	Permanent_Redirect(308),
	
	//4xx
	Bad_Request(400),
	Unauthorized(401),
	Payment_Required(402),
	Forbidden(403),
	Not_Found(404),
	Method_Not_Allowed(405),
	Not_Acceptable(406),
	Proxy_Authentication_Required(407),
	Request_Timeout(408),
	Conflict(409),
	Gone(410),
	Length_Required(411),
	
	//5xx
	Internal_Server_Error(500);
	
	
	public String toString() {
		return super.toString().replace("_", " ");
	}
	
	private final int code;
	private StatusCode(int i){
		code = i;
	}
	
	public int code(){
		return code;
	}
}
