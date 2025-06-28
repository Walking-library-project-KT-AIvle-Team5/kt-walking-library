<template>
    <div>
        <Boolean
            label="IsKtCustomer"
            v-model="value.isKtCustomer"
            :editMode="editMode"
        />
        <String
            label="LoginId"
            v-model="value.loginId"
            :editMode="editMode"
        />
        <String
            label="Password"
            v-model="value.password"
            :editMode="editMode"
        />
        <String
            label="Name"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="Role"
            v-model="value.role"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'MemberManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "memberManagements",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
