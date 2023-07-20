# Devheat_Beta_Maha-Devs
**Repository for team project under GDSC IIIT Surat.**

---
We can boil down the problem statement to these basic VALUES:
## ACCESSIBILITY FUNCTIONALITY USABILITY
---
We are trying to provide a solution for vendors and stall owners who wish to maintain a record of GST invoices and
provide customers a legitimate invoice.

We understand that vendors and stall owners don't have the privilege to own and maintain heavy equipment like a Desktop computer
So sticking to the problem statement we put our content in a Mobile Webview and provided local storage for the Bills to decrease 
hassle for our clients.(**छोटे व्यवसायी**)

As vendors are not known to be very tech-savvy.
We provide an easy to use interface. Rather than using forms to type out the amounts, we use large buttons
with image icons to make the usage obvious.


![image](https://user-images.githubusercontent.com/90756795/175781058-eaffeca8-00a7-4b0c-ac2e-ed00b8378e10.png)

---
With the skills that we had and the time provided, we chose Web based native application as our implementation method.

Another reason for choosing to implement our solution as a native app is that it **DOES NOT REQUIRE AN ACTIVE INTERNET CONNECTION**.
Which even if present in remote areas is used sparingly. Our app stores and retrieves data from local storage. 
We also store the required PDFs on the clients device so that it is available at a moments notice.
This also helps our Clients feel secure about their business data's PRIVACY.

---
## FUTURE PROSPECTS

Given that the UI of our application is web based, It is easier to port our application for other operating systems.
We can just create containers for the respective systems. Right now we serve only small business owners but in future we can
expand our customer base to Large Business owners because of the versatility provided by Web based applications.
We can move Storage of data for our Larger clients to the Cloud and Also Train models on the data to help them predict 
consumption and manage their Inventories.

---
## TECHNOLOGIES USED

### Languages:
- **HTML**    for Markup of UI
- **CSS**    for Styling the UI
- **JS**      for UI and data functionality
- **JAVA**   for Storage of data and Rendering the website in Webview

---
## BASIC OVERVIEW

The **Markup** for the website is laid down in a very efficient manner. The naming of Classes and IDs has been done in a fashion
that gives intuition about the content in them. The markup is designed to be responsive for a large range of Screen sizes.
the components of the website are designed to match their functionality.
The theme was selected after much research so that it makes the application appealing and easy on the eyes.
Apart from the design a seperate page is dedicated to depict the working of the application for a new user which makes it 
more user friendly.


The **Javascript** code adds the items to the list and displays their individual quantities, totals, a subtotal and the amount payable.
It also adds the markup for a remove button to each item added on the list. When an item is completely removed then it adjusts its 
table items accordingly. It is also used to validate the form details submitted by the user in the Your Account section of the App.
It is also used to call JAVA functions using the @JavascriptInterface in JAVA. It helped pass data from the webapp to JAVA so that 
it can be stored and retrieved later.

```
        @JavascriptInterface
        public String add_user_data(String json_string){
            return User.AddUserData(json_string,mContext);
        }
```

**JAVA** is used to create an Android web container with appropriate settings. It also helps establish a Javascript inteface and to 
receive and store user data in user.json file. It is also used to pass on data to Javascript. It is also used to prompt the user for
necessary usage permissions. And to store the generated invoice as a PDF in the Downloads folder.

PROJECT VIDEO LINK : https://drive.google.com/file/d/1n_pOABqFTvMYnN-6VMIA-FwWQ0Nn69Hd/view?usp=drivesdk

