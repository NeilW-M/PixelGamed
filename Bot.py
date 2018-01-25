import discord
from discord.ext.commands import Bot
from discord.ext import commands
import asyncio
import time

Client = discord.Client()
client = commands.Bot(command_prefix = "?")

@client.event
async def on_ready():
    print("Bot has connected.")

@client.event
async def on_message(message):
    # Pins the next message if the pin command was called
    # if will_pin:
    #    await client.pin_message(message)
    #    await client.send_message(message.channel, "\"" + message.content + "\" was pinned to the channel")
    #    will_pin = Falsee
    # Pins the user's message
    if message.content.upper().startswith("?PIN"):
        message = await client.get_message(message.channel)
        await client.pin_message(message)
    # Tests to see if the bot is responding
    if message.content.upper() == "?TESTBOT":
        await client.send_message(message.channel, "Responding.")
    # Responds to the user with Pong!
    if message.content.upper().startswith("?PING"):
        userID = message.author.id
        await client.send_message(message.channel, "<@%s> Pong!" % (userID))
    # Has the bot message a specific user message
    if message.content.upper().startswith("?SAY"):
        await client.delete_message(message)
        if message.author.id == "175441095210041344":
            if len(message.content) > 5:
                args = message.content.split(" ")
                await client.send_message(message.channel, "%s" % (" ".join(args[1:])))
            else :
                await client.send_message(message.channel, "Please enter something to say.")
        else:
            await client.send_message(message.channel, "You lack permission to perform the specified command.")
    # Has the bot lists all of the possible user commands
    if message.content.upper() == "?HELP":
        command_list = ["Testbot", "Ping", "Say", "Pin"]
        await client.send_message(message.channel, "All commands are preceded by a \'?\'.\n--------")
        for com in command_list:
            await client.send_message(message.channel, "-\t" + com)

client.run("NDA1NTA5MDc1MTAzNjQ1NzA4.DUlvFA._511TgFqUQeeY6-09F9fAplvnr0")

