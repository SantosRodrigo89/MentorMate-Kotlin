package br.com.fiap.mentormate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.mentormate.CommonDivider
import br.com.fiap.mentormate.CommonProgressSpinner
import br.com.fiap.mentormate.DestinationScreen
import br.com.fiap.mentormate.MMViewModel
import br.com.fiap.mentormate.navigateTo

enum class Gender {
    MALE, FEMALE, ANY
}

@Composable
fun ProfileScreen(navController: NavController, vm: MMViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
    else {
        val userData = vm.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }
        var gender by rememberSaveable {
            mutableStateOf(
                Gender.valueOf(
                    userData?.gender?.uppercase() ?: "MALE"
                )
            )
        }
        var genderPreference by rememberSaveable {
            mutableStateOf(
                Gender.valueOf(
                    userData?.genderPreference?.uppercase() ?: "FEMALE"
                )
            )
        }

        val scrollState = rememberScrollState()

        Column {
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(8.dp),
                vm = vm,
                name = name,
                username = username,
                bio = bio,
                gender = gender,
                genderPreference = genderPreference,
                onNameChange = { name = it },
                onUsernameChange = { username = it },
                onBioChange = { bio = it },
                onGenderChange = { gender = it },
                onGenderPreferenceChange = { genderPreference = it },
                onSave = {
                    vm.updateProfileData(name, username, bio, gender, genderPreference)
                },
                onBack = { navigateTo(navController, DestinationScreen.Swipe.rout) },
                onLogout = {
                    vm.onLogout()
                    navigateTo(navController, DestinationScreen.Login.rout)
                }

            )

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILE,
                navController = navController
            )
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: MMViewModel,
    name: String,
    username: String,
    bio: String,
    gender: Gender,
    genderPreference: Gender,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onGenderPreferenceChange: (Gender) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })

        }


        CommonDivider()

        // ProfileImage()

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = onNameChange,
                modifier = Modifier.background(Color.Transparent),
                textStyle = TextStyle(color = Color.Black)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                modifier = Modifier.background(Color.Transparent),
                textStyle = TextStyle(color = Color.Black)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Bio", modifier = Modifier.width(100.dp))
            TextField(
                value = bio,
                onValueChange = onBioChange,
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(150.dp),
                textStyle = TextStyle(color = Color.Black),
                singleLine = false
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "I am a:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == Gender.MALE,
                    onClick = { onGenderChange(Gender.MALE) })
                Text(
                    text = "Man",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.MALE) })
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == Gender.FEMALE,
                    onClick = { onGenderChange(Gender.FEMALE) })
                Text(
                    text = "Woman",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.MALE) })
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Looking for:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.MALE,
                        onClick = { onGenderPreferenceChange(Gender.MALE) })
                    Text(
                        text = "Men",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.MALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.FEMALE,
                        onClick = { onGenderPreferenceChange(Gender.FEMALE) })
                    Text(
                        text = "Women",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.FEMALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.ANY,
                        onClick = { onGenderPreferenceChange(Gender.ANY) })
                    Text(
                        text = "Any",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.ANY) })
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() })
        }
    }
}


@Composable
fun ProfileImage() {
    TODO("Not yet implemented")
}




