Test1: User creates an account, sends message and exports message

Steps: Launch server then 2 clients simultaneously for this example

1. User1 launches application
2. User1 selects to "create" an account
3. User1 selects to make a "seller" account
4. User1 enters email "bob@gmail.com
5. User1 enters password "password1"
6. User1 enters name "Bob"
7. User1 repeats steps 4 and 5
8. User1 selects to create a store
9. User1 enters "Walmart" for the name of the store
10. User2 selects the "create" account option
11. User2 selects to make a "customer" account
12. User2 enter the invalid email "bill@"
13. User2 is presented an invalid format message and asked to enter their email again
14. User2 enters email "bill@gmail.com"
15. User2 enters password "password2"
16. User2 enters name "Bill" and enters in email and password again
17. User2 selects the "send message" option
18. User2 selects the "stores" option
19. User2 selects "Walmart"
20. User2 selects the "Message" option
21. User2 types the message "Hello, I am interested in one of your products"
22. User2 selects the selects the "send message" option
23. User2 selects the "sellers" option
24. User2 types the email "bob@gmail.com"
25. User2 selects "Message"
26. User2 types "I just wanted to make sure that you are the owner of Walmart"
27. User2 selects export message

Tests the customer's send message and export message:
Expected Result: 
"Time, Sender, Recipient, Message
(Time of message sent) , bill@gmail.com , bob@gmail.com, Hello, I am interested in one of your products
(Time of message sent) , bill@gmail.com , bob@gmail.com, I just wanted to make sure that you are the owner of Walmart"

28. User1 selects "send message"
29. User1 selects "List"
30. User1 selects "Bill"
31. User1 selects "Message"
32. User1 types "Yes I am the owner of Walmart. What product are you interested in?"
33. User1 selects export message

Tests the seller's send and export message

Expected Result: 
"Time, Sender, Recipient, Message
(Time of message received) , bill@gmail.com , bob@gmail.com, Hello, I am interested in one of your products
(Time of message received) , bill@gmail.com , bob@gmail.com, I just wanted to make sure that you are the owner of Walmart
(Time of message received) , bob@gmail.com , bill@gmail.com, Yes I am the owner of Walmart. What product are you interested in?"

Test 2: blocking a user and logging out

1. User selects block user
2. User selects Bill
3. User selects send message
4. User does not see Bill there
5. User clicks "view messages"
6. User clicks "Okay"
7. User is asked if they want to refresh
8. User clicks "Yes"
9. User is back in view messages
10. User clicks okay
11. Use clicks no to refresh
11. User clicks log off
12. User exits the program and is greeted with a farewell message
13. The Server saves the data

Test 3: Deleting and editing

1. User 1 sends user 2 a message saying "Hi"
2. User 1 edits the message to "Fix"
3. User 2 clicks on view message
4. User 2 sees "Fix"
5. User 2 exports the messages
6. The edited message in the file says "edited"
5. User 1 sends "Hello" to User 2
6. User 1 clicks on delete message
7. User 2 does not see the message
