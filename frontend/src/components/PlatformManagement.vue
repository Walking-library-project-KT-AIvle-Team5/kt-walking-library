<template>
    <div>
        <String
            label="SubscribedUserIds"
            v-model="value.subscribedUserIds"
            :editMode="editMode"
        />
        <String
            label="RecommendationMessage"
            v-model="value.recommendationMessage"
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
    name: 'PlatformManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "platformManagements",
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
