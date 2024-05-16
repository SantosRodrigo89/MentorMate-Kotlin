package br.com.fiap.mentormate.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import br.com.fiap.mentormate.CommonImage
import br.com.fiap.mentormate.CommonProgressSpinner
import br.com.fiap.mentormate.DestinationScreen
import br.com.fiap.mentormate.MMViewModel
import br.com.fiap.mentormate.navigateTo

enum class Gender {
    MENTOR, MENTORADO, ANY
}

@Composable
fun ProfileScreen(navController: NavController, vm: MMViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
    else {
        val userData = vm.userData.value
        val g = if (userData?.gender.isNullOrEmpty()) "MENTOR"
        else userData!!.gender!!.uppercase()
        val gPref = if (userData?.genderPreference.isNullOrEmpty()) "MENTORADO"
        else userData!!.genderPreference!!.uppercase()
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }
        var experience by rememberSaveable { mutableStateOf(userData?.experience ?: "") }
        var educationalBackground by rememberSaveable { mutableStateOf(userData?.educationalBackground ?: "") }
        var gender by rememberSaveable { mutableStateOf(Gender.valueOf(g)) }
        var genderPreference by rememberSaveable { mutableStateOf(Gender.valueOf(gPref)) }

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
                experience = experience,
                educationalBackground = educationalBackground,
                gender = gender,
                genderPreference = genderPreference,
                onNameChange = { name = it },
                onUsernameChange = { username = it },
                onBioChange = { bio = it },
                onExperienceChange = { experience = it },
                onEducationalBackgroundChange = { educationalBackground = it },
                onGenderChange = { gender = it },
                onGenderPreferenceChange = { genderPreference = it },
                onSave = {
                    vm.updateProfileData(
                        name,
                        username,
                        bio,
                        experience,
                        educationalBackground,
                        gender,
                        genderPreference
                    )
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
    experience: String,
    educationalBackground: String,
    gender: Gender,
    genderPreference: Gender,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onExperienceChange: (String) -> Unit,
    onEducationalBackgroundChange: (String) -> Unit,
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
            Text(text = "Voltar", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Salvar", modifier = Modifier.clickable { onSave.invoke() })

        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Nome", modifier = Modifier.width(100.dp))
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
            Text(text = "Usuário", modifier = Modifier.width(100.dp))
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
            Text(text = "Sobre mim", modifier = Modifier.width(100.dp))
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Experiência", modifier = Modifier.width(100.dp))
            TextField(
                value = experience,
                onValueChange = onExperienceChange,
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Formação acadêmica", modifier = Modifier.width(100.dp))
            TextField(
                value = educationalBackground,
                onValueChange = onEducationalBackgroundChange,
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
                text = "Quero ser :", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == Gender.MENTOR,
                    onClick = { onGenderChange(Gender.MENTOR) })
                Text(
                    text = "Mentor",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.MENTOR) })
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == Gender.MENTORADO,
                    onClick = { onGenderChange(Gender.MENTORADO) })
                Text(
                    text = "Mentorado",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.MENTOR) })
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
                text = "Tipo de Mentoria:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.MENTOR,
                        onClick = { onGenderPreferenceChange(Gender.MENTOR) })
                    Text(
                        text = "Presencial",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.MENTOR) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.MENTORADO,
                        onClick = { onGenderPreferenceChange(Gender.MENTORADO) })
                    Text(
                        text = "Online",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.MENTORADO) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == Gender.ANY,
                        onClick = { onGenderPreferenceChange(Gender.ANY) })
                    Text(
                        text = "Ambas",
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
            Text(text = "Sair", modifier = Modifier.clickable { onLogout.invoke() })
        }
    }
}


@Composable
fun ProfileImage(imageUrl: String?, vm: MMViewModel) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { vm.uploadProfileImage(uri) }
        }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(data = imageUrl)
            }
            Text(text = "Alterar foto")
        }

        val isLoading = vm.inProgress.value
        if (isLoading)
            CommonProgressSpinner()
    }
}




