package com.tunehub.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.tunehub.entities.Users;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller

public class PaymentController {
	@Autowired
	UsersService service;

	@GetMapping("/pay")
	public String pay() {
		return "makePayment";
	}
	@GetMapping("/payment-successful")
	public String paymentSuccess(HttpSession session) {
		String mail = (String) session.getAttribute("email");
		Users u = service.getUser(mail);
		u.setPremium(true);
		service.updateUser(u);
		return "premiumHome";
	}

	@GetMapping("/payment-failed")
	public String paymentFailure() {
		System.out.println("Payment fails");
		return "customerHome";
	}
	
	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder(HttpSession session) {

		int  amount  = 5000;
		Order order=null;
		try {
			RazorpayClient razorpay=new RazorpayClient("rzp_test_rYfrB1X0sV11af", "0SLo3TeBfzzijJZkofEIIzJG");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount*100); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");

			order = razorpay.orders.create(orderRequest);

			String mail =  (String) session.getAttribute("email");

			Users u = service.getUser(mail);
			u.setPremium(true);
			service.updateUser(u);

		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		finally {
			return order.toString();
		}
	}
	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,@RequestParam String signature) {
		try {
			// Initialize Razorpay client with your API key and secret
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_rYfrB1X0sV11af", "0SLo3TeBfzzijJZkofEIIzJG");
			// create a signature verification data
			String verificationData = orderId + "|" + paymentId;

			// use razorpay's utility function to verify the signature
			boolean isValidSignature = Utils.verifySignature(verificationData,signature,"0SLo3TeBfzzijJZkofEIIzJG");

			return isValidSignature;
		} catch (RazorpayException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}